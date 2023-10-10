package repositories;

import java.sql.*;
import java.util.UUID;

public class CookiesRepositoryJdbcImpl implements CookiesRepository {

    private final Connection connection;
    private final Statement statement;

    public CookiesRepositoryJdbcImpl(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    @Override
    public void saveSession(UUID uuid, long userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO session_data (user_id, uuid) VALUES (?, ?)");
            statement.setLong(1, userId);
            statement.setObject(2, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean findSession(UUID uuid) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM session_data WHERE uuid = ?");
            statement.setObject(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

