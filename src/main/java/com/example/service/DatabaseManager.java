package com.example.service;

import com.example.exception.UserNotFoundException;
import com.example.model.User;

import java.sql.*;

public class DatabaseManager {
    private static final String url = "jdbc:postgresql://192.168.1.53:5432/mydb";
    private static final String dataBaseUser = "postgres";
    private static final String password = "pgPwd123";


    // Метод для получения пользователя по логину
    public static User getUserByLogin(String login) throws SQLException {
        String query = "SELECT l.login, l.pwd, l.dt, e.email " +
                "FROM usrmanager.logins l " +
                "JOIN usrmanager.emails  e ON l.login = e.login " +
                "WHERE l.login = '" + login + "'";

        User userFromResult = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url, dataBaseUser, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String resultLogin = resultSet.getString("login");
                String resultPassword = resultSet.getString("pwd");
                Date resultDate = resultSet.getDate("dt");
                String resultEmail = resultSet.getString("email");

                userFromResult = new User(resultLogin, resultPassword, resultDate, resultEmail);
            } else {
                throw new UserNotFoundException("user c login = " + login + " не найден.");
            }
        } catch (SQLException exception) {
            throw new SQLException("Ошибка при добавлении пользователя в базу данных: " + exception.getMessage(), exception);
        } finally {
            closeQuietly(resultSet);
            closeQuietly(statement);
            closeQuietly(connection);
        }
        return userFromResult;
    }

    // Вспомогательный метод для закрытия ресурсов
    private static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при закрытии ресурса: " + closeable, e);
            }
        }
    }

    // Метод для вставки в таблицы нового пользователя
    public static int insertUser(User user) throws SQLException {
        String insertQuery = "INSERT INTO usrmanager.logins (login, pwd, dt) VALUES (?, ?, ?); " +
                "INSERT INTO usrmanager.emails (login, email) VALUES (?, ?);";
        try (Connection connection = DriverManager.getConnection(url, dataBaseUser, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPwd());
            preparedStatement.setDate(3,   new Date(user.getDt().getTime()));
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getEmail());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected;

        } catch (SQLException exception) {
            throw new SQLException("Ошибка при добавлении пользователя в базу данных: " + exception.getMessage(), exception);
        }
    }
}
