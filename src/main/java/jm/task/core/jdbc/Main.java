package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Ivan", "Petrov", (byte) 22);
        userService.saveUser("Nikolai", "Ivanov", (byte) 45);
        userService.saveUser("Elizaveta", "Smirnova", (byte) 30);
        userService.saveUser("Pavel", "Ryazancev", (byte) 18);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
