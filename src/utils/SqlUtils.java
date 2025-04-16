package utils;

import java.sql.*;
import java.util.ArrayList;

public class SqlUtils {

    public static final String URL = "jdbc:sqlite:databases/webbutiken.db";

    // Get a database connection
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
            return conn;
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "connecting to the database");
            throw e;
        }
    }

    // Execute SELECT and map the result inside the method to avoid closing problems
    public static <T> ArrayList<T> executeAndMap(String query, RowMapper<T> mapper, String... params) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setString(i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<T> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapper.mapRow(rs));
                }
                return result;
            }
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "executing mapped query: " + query);
            throw e;
        }
    }

    // Execute INSERT/UPDATE/DELETE
    public static void executeUpdate(String query, Object... params) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "executing update query: " + query);
            throw e;
        }
    }

    public static int executeInsertAndReturnId(String query, Object... params) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            } else {
                throw new SQLException("No generated key returned.");
            }

        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "executing insert with return ID: " + query);
            throw e;
        }
    }
}