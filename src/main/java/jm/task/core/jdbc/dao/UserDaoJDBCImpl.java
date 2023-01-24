package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection dbConnection = Util.getDbConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS users (
                      id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                      name VARCHAR(45) NOT NULL,
                      lastname VARCHAR(45) NOT NULL,
                      age TINYINT(3) NOT NULL,
                      PRIMARY KEY (id));""");
        } catch (SQLException e) {
            System.out.println("Ошибка создания БД");
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try(Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users;");
        } catch (SQLException e) {
            System.out.println("Ошибка удаления БД");
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO users(name, lastname, age) VALUES(?,?,?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка добавления пользователя");
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement statement = dbConnection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
        } catch (SQLException e) {
            System.out.println("Ошибка удаления пользователя");
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new LinkedList<>();

        try(Statement statement = dbConnection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("lastname"), rs.getByte("age"));
                user.setId(rs.getLong("id"));
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения всех пользователей");
            throw new RuntimeException(e);
        }


        return list;
    }

    public void cleanUsersTable() {
        try(Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate("TRUNCATE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
