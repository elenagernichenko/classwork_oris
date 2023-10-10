package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CookiesRepositoryJdbcImpl implements CookiesRepository {

    private final Connection connection;

    public CookiesRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
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

