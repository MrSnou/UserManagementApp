package com.example.usermanagement.dao;

import com.example.usermanagement.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class UserDao {
    private static List<User> users = new ArrayList<>();
    private static int idCounter = 1;


    public void addUser(User user) {
        user.setId(idCounter++);
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public void deleteUser(int id) {
        users.removeIf(u -> u.getId() == id);
    }

    public void addRandomUsers(int numberOfUsers) {
        Random random = new Random();
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve"};
        String[] domains = {"example.com", "mail.com", "test.org"};

        for (int i = 0; i < numberOfUsers; i++) {
            User u = new User();
            u.setId(idCounter++);
            String name = names[random.nextInt(names.length)] + i;
            u.setUserName(name);
            u.setPassword("pass" + i);
            u.setEmail(name.toLowerCase() + "@" + domains[random.nextInt(domains.length)]);
            users.add(u);
        }
    }

    public void editUser(int id, String userName, String password, String email) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setUserName(userName);
                user.setPassword(password);
                user.setEmail(email);
                break;
            }
        }
    }
}
