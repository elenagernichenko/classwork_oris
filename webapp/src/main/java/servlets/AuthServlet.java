package servlets;

import models.User;
import repositories.CookiesRepository;
import repositories.CookiesRepositoryJdbcImpl;
import repositories.UsersRepository;
import repositories.UsersRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "5058";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/servlets_db";
    private UsersRepository usersRepository;
    private CookiesRepository cookiesRepository;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            usersRepository = new UsersRepositoryJdbcImpl(connection, statement);
            cookiesRepository = new CookiesRepositoryJdbcImpl(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("session_uuid")) {
                    String uuidString = cookie.getValue();
                    UUID uuid = UUID.fromString(uuidString);

                    if (cookiesRepository.findSession(uuid)) {
                        response.sendRedirect("users");
                        return;
                    }
                }
            }
        }
        request.getRequestDispatcher("/html/auth.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> user = usersRepository.findByLogin(User.builder()
                .username(username)
                .password(password)
                .build());

        String result;
        String status;

        if (user.isPresent()) {
            UUID uuid = UUID.randomUUID();
            Cookie cookie = new Cookie("session_uuid", uuid.toString());
            response.addCookie(cookie);

            cookiesRepository.saveSession(uuid, user.get().getId());

            result = username + ", you have successfully logged in to your account";
            status = "login in successfully";
        } else {
            result = "user " + username + " not found. try again!";
            status = "login failed :(";
        }
        request.setAttribute("resultOfAuth", result);
        request.setAttribute("status", status);
        request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
    }
}
