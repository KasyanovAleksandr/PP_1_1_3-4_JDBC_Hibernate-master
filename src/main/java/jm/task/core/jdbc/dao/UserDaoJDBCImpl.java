package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection connection;
    private static final String CREATE_USERS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(40), last_name VARCHAR(40), age INT, PRIMARY KEY(id))";
    private static final String DROP_USERS_TABLE_SQL = "DROP TABLE IF EXISTS users";
    private static final String SAVE_USER_SQL = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
    private static final String REMOVE_USER_BY_ID_SQL = "DELETE FROM users WHERE id=?";
    private static final String GET_ALL_USERS_SQL = "SELECT * FROM users";
    private static final String CLEAN_USERS_TABLE_SQL = "DELETE FROM users";

    public UserDaoJDBCImpl() {connection = Util.getConnection();}

    public void createUsersTable() { //создание таблицы
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_USERS_TABLE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() { //удаление таблицы, если в ней существуют записи
        try (Statement statement = connection.createStatement()) {
            statement.execute(DROP_USERS_TABLE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) { //Добавление User в таблицу
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) { //Удаление User из таблицы ( по id )
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() { //Получение всех User(ов) из таблицы
        List<User> users = new ArrayList<>(); // завожу Лист users для возврата
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(Long.valueOf(resultSet.getString(1)));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() { //Очистка содержания таблицы
        try (Statement statement = connection.createStatement()){
            statement.execute(CLEAN_USERS_TABLE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
