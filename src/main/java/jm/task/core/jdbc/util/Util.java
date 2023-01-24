package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/practice1.1.4";
    private static final String USERNAME = "AltoCleff";
    private static final String PASSWORD = "root";
    public static Connection getDbConnection(){
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
            throw new RuntimeException(e);
        }
    }
}
