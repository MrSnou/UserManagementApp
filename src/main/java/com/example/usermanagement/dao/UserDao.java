package com.example.usermanagement.dao;

import com.example.usermanagement.model.User;
import java.util.ArrayList;
import java.util.List;


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
}
