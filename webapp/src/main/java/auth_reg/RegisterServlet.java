package auth_reg;

import servlets.UsersRepository;
import servlets.UsersRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/reg")
public class RegisterServlet extends HttpServlet {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "5058";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/servlets_db";
    private UsersRepository usersRepository;

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/register.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String result;
        String status;

        if (usersRepository.findUserByEmail(email)) {
            result = "User " + username + " already exists with this email";
            status = "registration failed";
        } else {
            if (!password.isEmpty() && !username.isEmpty() && !email.isEmpty()) {
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     Statement statement = connection.createStatement()) {
                    String sql = "INSERT INTO users(username, email, password) VALUES ('" + username + "', '" + email + "', '" + password + "');";
                    statement.executeUpdate(sql);

                    result = "You are registered! Good luck, " + username;
                    status = "registration completed";
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    result = "User registration failed";
                    status = "registration failed";
                }
            } else {
                result = "User is not registered. Please fill in all fields.";
                status = "registration failed";
            }
        }

        request.setAttribute("resultOfAuth", result);
        request.setAttribute("status", status);
        request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
    }
}
