package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import net.bytebuddy.asm.MemberSubstitution;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "rootroot";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static SessionFactory sessionFactory;


    public static Connection getConnection() {
            try {
                return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
           try {
               Properties prop = new Properties();
               prop.setProperty("hibernate.connection.url", DB_URL);
               prop.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");
               prop.setProperty("hibernate.connection.username", DB_USERNAME);
               prop.setProperty("hibernate.connection.password", DB_PASSWORD);
               SessionFactory sessionFactory = new Configuration()
                       .addProperties(prop)
                       .addAnnotatedClass(User.class)
                       .buildSessionFactory();

               return sessionFactory;
           } catch (HibernateException e) {
              throw new  RuntimeException(e);
           }
        }

        return sessionFactory;
    }


}
