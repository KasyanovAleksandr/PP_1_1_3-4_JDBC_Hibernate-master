package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

// - запросы храни в каждом методе. - ОК
//
//- в каждом методе ты закрываешь стейтмент ресурсах, а нужно там же закрывать еще и коннекшн - ОК
//
//- препаред стейтмент используй с запросами с параметрами а обычный там где параметров нет - ОК

    public UserDaoJDBCImpl() {}

    public void createUsersTable() { //создание таблицы
        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(40), last_name VARCHAR(40), age INT, PRIMARY KEY(id))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() { //удаление таблицы, если в ней существуют записи
        try (Connection connection = new Util().getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) { //Добавление User в таблицу
        try (Connection connection = new Util().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) { //Удаление User из таблицы ( по id )
        try (Connection connection = new Util().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() { //Получение всех User(ов) из таблицы
        List<User> users = new ArrayList<>(); // завожу Лист users для возврата
        try (Connection connection = new Util().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, last_name, age FROM users")) {
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
        try (Connection connection = new Util().getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
