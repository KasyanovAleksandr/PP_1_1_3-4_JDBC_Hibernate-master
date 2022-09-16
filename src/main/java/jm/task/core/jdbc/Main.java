package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    public static void main(String[] args) throws SQLException {

        //тут надо создать экземпляр сервиса и пользоваться им - OK

        final UserService userService = new UserServiceImpl();
        String[][] userTest = {{"Шахбаз", "Шариф", "60"},
                {"Сердар", "Бердымухамедов", "55"},
                {"Эбрахим", "Раиси", "70"},
                {"Папа", "Римский", "80"}};
        userService.dropUsersTable();
        System.out.println("Таблица удалена (если была)");
        userService.createUsersTable();
        System.out.println("Таблица создана");

        userService.saveUser(userTest[0][0], userTest[0][1], (byte) Integer.parseInt(userTest[0][2]));
        userService.saveUser(userTest[1][0], userTest[1][1], (byte) Integer.parseInt(userTest[1][2]));
        userService.saveUser(userTest[2][0], userTest[2][1], (byte) Integer.parseInt(userTest[2][2]));
        userService.saveUser(userTest[3][0], userTest[3][1], (byte) Integer.parseInt(userTest[3][2]));
        System.out.println("Юзеры занесены в твблицу");
        List<User> users = userService.getAllUsers();
        System.out.println("Юзеры получены из таблицы, сейчас выведу...");
        System.out.println(users.toString());
        System.out.println("Удаляем все строки в таблице, не записывая в журнал транзакций удаление отдельных строк данных.");
        userService.cleanUsersTable();
        users = userService.getAllUsers();
        System.out.println("Юзеры получены из таблицы, сейчас выведу...");
        System.out.println(users.toString());
        System.out.println("ХА-ХА-ХА! Их там нет.");
    }
}
