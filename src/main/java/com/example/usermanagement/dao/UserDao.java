package com.example.usermanagement.dao;

import com.example.usermanagement.dbutil.HibernateUtil;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;
import jakarta.servlet.ServletContextEvent;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class UserDao {

    public void addUser(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            user.setPassword(passwordHashing(user.getPassword()));
            session.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public User getUserByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteUser(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            tx.commit();
        }  catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void addRandomUsers(int numberOfUsers) {
        Random random = new Random();
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve"};
        String[] domains = {"example.com", "mail.com", "test.org"};

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Role userRole = session.createQuery("FROM Role WHERE name = :name", Role.class)
                    .setParameter("name", "ROLE_USER")
                    .uniqueResult();

            for (int i = 0; i < numberOfUsers; i++) {
                User u = new User();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
                LocalDateTime now = LocalDateTime.now();
                String time = now.format(formatter);

                String name = names[random.nextInt(names.length)] + "_" + time + "_" + i;
                u.setUsername(name);
                u.setPassword(passwordHashing("pass" + i));
                u.setEmail(name.toLowerCase() + "@" + domains[random.nextInt(domains.length)]);
                u.setRole(userRole);

                boolean exists = session.createQuery("SELECT count(u) > 0 FROM User u WHERE u.username = :username", Boolean.class)
                        .setParameter("username", name)
                        .uniqueResult();

                if (exists) {
                    continue;
                }
                session.persist(u);

                session.persist(u);
            }

            tx.commit();
        }
    }

    public void editUser(int id, String userName, String password, String email) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                user.setUsername(userName);
                user.setPassword(passwordHashing(password));
                user.setEmail(email);
                session.update(user);
            }
            tx.commit();
        }  catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
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

    public void mergeUser(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void initAdminDev() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            User adminDev = session.createQuery(
                            "FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", "AdminDev")
                    .uniqueResult();

            if (adminDev == null) {
                User newAdmin = new User();
                newAdmin.setUsername("AdminDev");
                newAdmin.setEmail("admin@example.com");
                newAdmin.setPassword(passwordHashing("admindev"));

                RoleDao roleDao = new RoleDao();
                Role userRole = roleDao.getRoleByName("ROLE_ADMINDEVELOPER");
                if (userRole == null) {
                    throw new RuntimeException("Default role ROLE_ADMINDEVELOPER not found in database!");
                }
                newAdmin.setRole(userRole);

                session.save(newAdmin);
                System.out.println("AdminDev created!");
            } else {
                System.out.println("AdminDev already exists.");
            }

            tx.commit();
        }
    }
}





















/** Legacy Code (SQL RAM db)
 *          Row reader from SQL db just to ez things out.
 * //    private User mapRow(ResultSet rs) throws SQLException {
 * //        User u = new User();
 * //        u.setId(rs.getInt("id"));
 * //        u.setUserName(rs.getString("username"));
 * //        u.setEmail(rs.getString("email"));
 * //        u.setPassword(rs.getString("password"));
 * //        return u;
 * //    }
 *
 *          Edit User
 * //        try (Connection c = DatabaseConnection.getConnection()) {
 * //            PreparedStatement ps = c.prepareStatement("UPDATE users Set username = ? , password = ? , email = ? where id = ?");
 * //            ps.setString(1, userName);
 * //            ps.setString(2, passwordHashing(password));
 * //            ps.setString(3, email);
 * //            ps.setInt(4, id);
 * //            ps.executeUpdate();
 * //            ps.close();
 * //        } catch (SQLException ex) {
 * //            ex.printStackTrace();
 * //        }
 *
 *          Delete User
 * //        String sql = "DELETE FROM users WHERE id = ?";
 * //        try (Connection c = DatabaseConnection.getConnection();
 * //             PreparedStatement ps = c.prepareStatement(sql)) {
 * //            ps.setInt(1, id);
 * //            ps.executeUpdate();
 * //        } catch (SQLException ex) {
 * //            ex.printStackTrace();
 * //        }
 *
 *          Get User by ID
 * //        String sql = "SELECT * FROM users WHERE id = ?";
 * //        try (Connection c = DatabaseConnection.getConnection();
 * //             PreparedStatement ps = c.prepareStatement(sql)) {
 * //            ps.setInt(1, id);
 * //            try (ResultSet rs = ps.executeQuery()) {
 * //                if (rs.next()) {
 * //                    return mapRow(rs);
 * //                }
 * //            }
 * //        } catch (SQLException ex) {
 * //            ex.printStackTrace();
 * //        }
 * //        return null;
 *
 *          Get User by Username
 * //        try (Connection c = DatabaseConnection.getConnection()) {
 * //            PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE username = ?");
 * //            ps.setString(1, username);
 * //            ResultSet rs = ps.executeQuery();
 * //
 * //            if (rs.next()) {
 * //                return mapRow(rs);
 * //            } else return null;
 * //
 * //
 * //        } catch (SQLException ex) {
 * //            ex.printStackTrace();
 * //        }
 *
 *          Get Users
 * //        List<User> users = new ArrayList<>();
 * //        try (Connection conn = DatabaseConnection.getConnection()) {
 * //            Statement stmt = conn.createStatement();
 * //            ResultSet rs = stmt.executeQuery("Select * from users");
 * //            while (rs.next()) {
 * //                users.add(mapRow(rs));
 * //            }
 * //            rs.close();
 * //            stmt.close();
 * //        }catch (SQLException ex) {
 * //            ex.printStackTrace();
 * //        }
 * //        return users;
 *
 *          Add User
 * //        try (Connection conn = DatabaseConnection.getConnection()) {
 * //            PreparedStatement stmt = conn.prepareStatement(
 * //                    "Insert Into users (username, email, password) Values (?, ?, ?)"
 * //            );
 * //            stmt.setString(1, user.getUserName());
 * //            stmt.setString(2, user.getEmail());
 * //            stmt.setString(3, passwordHashing(user.getPassword()));
 * //            stmt.executeUpdate();
 * //            stmt.close();
 * //
 * //        } catch (SQLException ex) {
 * //            ex.printStackTrace();
 * //        }
 */
