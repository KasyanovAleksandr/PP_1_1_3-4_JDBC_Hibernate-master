package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    public static void main(String[] args) throws SQLException {

        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Шахбаз", "Шариф", (byte) 60);
        userDao.saveUser("Сердар", "Бердымухамедов", (byte) 55);
        userDao.saveUser("Эбрахим", "Раиси", (byte) 70);
        userDao.saveUser("Папа", "Римский", (byte) 80);

        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();


        // реализуйте алгоритм здесь
    }
}
