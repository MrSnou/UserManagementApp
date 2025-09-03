package com.example.usermanagement.dao;

import com.example.usermanagement.dbutil.DatabaseConnection;
import com.example.usermanagement.model.User;

import javax.xml.crypto.Data;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserDao {
//    private static List<User> users = new ArrayList<>();
//    private static int idCounter = 1;


    public void addUser(User user) {
//        user.setId(idCounter++);
//        users.add(user);
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "Insert Into users (username, email, password) Values (?, ?, ?)"
            );
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, passwordHashing(user.getPassword()));
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from users");
            while (rs.next()) {
                users.add(mapRow(rs));
            }
            rs.close();
            stmt.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public User getUserByUsername(String username) {

        try (Connection c = DatabaseConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            } else return null;


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addRandomUsers(int numberOfUsers) {
        Random random = new Random();
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve"};
        String[] domains = {"example.com", "mail.com", "test.org"};

        for (int i = 0; i < numberOfUsers; i++) {
            User u = new User();
            String name = names[random.nextInt(names.length)] + i;
            u.setUserName(name);
            u.setPassword("pass" + i);
            u.setEmail(name.toLowerCase() + "@" + domains[random.nextInt(domains.length)]);
            addUser(u);
        }
    }

    public void editUser(int id, String userName, String password, String email) {
        try (Connection c = DatabaseConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement("UPDATE users Set username = ? , password = ? , email = ? where id = ?");
            ps.setString(1, userName);
            ps.setString(2, passwordHashing(password));
            ps.setString(3, email);
            ps.setInt(4, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    public String passwordHashing(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setUserName(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        return u;
    }
}
