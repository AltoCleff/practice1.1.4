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
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                CREATE TABLE IF NOT EXISTS users (
                  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                  name VARCHAR(45) NOT NULL,
                  lastname VARCHAR(45) NOT NULL,
                  age TINYINT(3) NOT NULL,
                  PRIMARY KEY (id));""")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка создания БД");
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement statement = dbConnection.prepareStatement("DROP TABLE IF EXISTS users;")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка удаления БД");
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO users(name, lastname, age) VALUES(?,?,?)")) {
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
        try (PreparedStatement statement = dbConnection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка удаления пользователя");
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new LinkedList<>();

        try (PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM users")) {
            ResultSet rs = statement.executeQuery();
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
        try (PreparedStatement statement = dbConnection.prepareStatement("TRUNCATE users")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
