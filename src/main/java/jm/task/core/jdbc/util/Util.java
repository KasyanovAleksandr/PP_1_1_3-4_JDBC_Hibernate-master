package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

//- не надо реализовывать синглтон для коннекшена - для каждого запроса свой коннекшн - ОК, синглтон родился после Трегулова :)

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "rootroot";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";


    public static Connection getConnection() {
            try {
                return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
