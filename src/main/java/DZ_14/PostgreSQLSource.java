package DZ_14;

import java.sql.*;

public class PostgreSQLSource implements Source {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/cache_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found", e);
        }
    }

    public PostgreSQLSource() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXISTS cache (key VARCHAR(255) PRIMARY KEY, value TEXT)";
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания таблицы : " + e.getMessage());
        }
    }


    @Override
    public void saveResult(String key, String value) {
        String sql = "INSERT INTO cache (key, value) VALUES (?, ?)" +
                "ON CONFLICT (key) DO UPDATE SET value = EXCLUDED.value";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, key);
            preparedStatement.setString(2, value);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения в БД : " + e.getMessage(), e);
        }
    }

    @Override
    public String getResult(String key) {
        String sql = "SELECT value FROM cache WHERE key = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, key);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("value");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения результата из БД " + e.getMessage(), e);
        }
        return null;
    }
}
