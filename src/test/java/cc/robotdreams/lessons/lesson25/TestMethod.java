package cc.robotdreams.lessons.lesson25;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestMethod {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/opencart-db";
    private static final String DB_USER = "opencart";
    private static final String DB_PASSWORD = "opencart";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            insertData(connection);
            selectData(connection);
            updateData(connection);
            deleteData(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertData(Connection connection) throws SQLException {
        String insertQuery = "INSERT INTO homework_user_data "
                + "(first_name, last_name, login, password_hash, date_of_birth) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setString(1, "John");
            insertStatement.setString(2, "Doe");
            insertStatement.setString(3, "jdoe");
            insertStatement.setString(4, "827ccb0eea8a706c4c34a16891f84e7b");
            insertStatement.setString(5, "1980-01-01");
            int rowsInserted = insertStatement.executeUpdate();
            System.out.println(rowsInserted + " row(s) inserted.");
        }
    }

    private static void selectData(Connection connection) throws SQLException {
        String selectQuery = "SELECT * FROM homework_user_data WHERE first_name = ?";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, "John");
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("First Name: " + resultSet.getString("first_name") +
                        ", Last Name: " + resultSet.getString("last_name"));
            }
        }
    }

    private static void updateData(Connection connection) throws SQLException {
        String updateQuery = "UPDATE homework_user_data SET last_name = ? WHERE first_name = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, "Smith");
            updateStatement.setString(2, "John");
            int rowsUpdated = updateStatement.executeUpdate();
            System.out.println(rowsUpdated + " row(s) updated.");
        }
    }

    private static void deleteData(Connection connection) throws SQLException {
        String deleteQuery = "DELETE FROM homework_user_data WHERE first_name = ?";

        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setString(1, "John");
            int rowsDeleted = deleteStatement.executeUpdate();
            System.out.println(rowsDeleted + " row(s) deleted.");
        }
    }
}
