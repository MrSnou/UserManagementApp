package com.example.usermanagement.dao;

import com.example.usermanagement.dbutil.HibernateUtil;
import com.example.usermanagement.dbutil.TestDataInitializer;
import com.example.usermanagement.dbutil.TestHibernateUtil;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoTests {
    private UserDao userDao;
    private SessionFactory sf;

    @BeforeAll
    void setup() {
        sf = TestHibernateUtil.getSessionFactory();
        TestDataInitializer.init(sf);
        userDao = new UserDao(sf);
    }

    @AfterAll
    void tearDown() {
        TestHibernateUtil.shutdown();
    }

    @Test
    void testUserExists() {
        User u = new User();
        u.setUsername("TestUser2");
        u.setPassword("pass");
        u.setEmail("Testuser2@mail.com");

        userDao.addUser(u);

        Assertions.assertThrows(Exception.class, () -> userDao.addUser(u));
        userDao.deleteUser(u.getId());
    }

    @Test
    void testGetUserByUsername() {
        User u = new User();
        u.setUsername("Alice");
        u.setPassword("pass");
        u.setEmail("alice@example.com");

        userDao.addUser(u);

        User actualUser = userDao.getUserByUsername("Alice");
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals("Alice", actualUser.getUsername());
        userDao.deleteUser(u.getId());
    }

    @Test
    void testAddUser() {
        User u = new User();
        u.setUsername("TestUser");
        u.setPassword("pass");
        u.setEmail("Testuser@mail.com");

        userDao.addUser(u);

        User actualUser = userDao.getUserByUsername("TestUser");
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals("TestUser", actualUser.getUsername());
        userDao.deleteUser(u.getId());
    }

    @Test
    void testGetUserByID() {
        User u = new User();
        u.setUsername("Bruce");
        u.setPassword("pass");
        u.setEmail("alice@example.com");

        userDao.addUser(u);

        int generatedId = u.getId();
        User actualUser = userDao.getUserById(generatedId);

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals("Bruce", actualUser.getUsername());
        Assertions.assertEquals(generatedId, actualUser.getId());
        userDao.deleteUser(u.getId());
    }

    @Test
    void testGetDeletedUser() {
        if ((userDao.getUserByUsername("Alice")) != null) {
            User user = userDao.getUserByUsername("Alice");
            int userID = user.getId();
            userDao.deleteUser(userID);

            Assertions.assertNull(userDao.getUserById(userID));
        } else {
            Assertions.assertNull(userDao.getUserByUsername("Alice"));
        }
    }

    @Test
    void testGetAllUsers() {
        List<User> emptyList = Collections.emptyList();
        User u1 = new User(); User u2 = new User (); User u3 = new User ();
        u1.setUsername("TestUser1"); u2.setUsername("TestUser2"); u3.setUsername("TestUser3");
        u1.setPassword("pass1"); u2.setPassword("pass2"); u3.setPassword("pass3");
        u1.setEmail("TestUser1@mail.com"); u2.setEmail("TestUser2@mail.com"); u3.setEmail("TestUser3@mail.com");
        userDao.addUser(u1); userDao.addUser(u2); userDao.addUser(u3);

        List<User> filledList = userDao.getUsers();

        Assertions.assertNotEquals(filledList, emptyList);
        Assertions.assertNotNull(filledList);
        Assertions.assertTrue(filledList.size() >= 3);
        userDao.deleteUser(u1.getId()); userDao.deleteUser(u2.getId()); userDao.deleteUser(u3.getId());
    }

    @Test
    void testAddNullUser() {
        Assertions.assertThrows(Exception.class, () -> userDao.addUser(null));
    }

    @Test
    void testAddUserWithNullUsername() {
        User u = new User();
        u.setPassword("pass"); u.setEmail("mail@mail.com");

        u.setUsername(null);

        Assertions.assertThrows(Exception.class, () -> userDao.addUser(u));
    }
    @Test
    void testAddUserWithNullPassword() {
        User u = new User();
        u.setUsername("TestUser"); u.setEmail("mail@mail.com");

        u.setPassword(null);

        Assertions.assertThrows(Exception.class, () -> userDao.addUser(u));
    }

    @Test
    void testAddUserWithNullEmail() {
        User u = new User();
        u.setUsername("TestUser"); u.setPassword("pass");

        u.setEmail(null);

        Assertions.assertThrows(Exception.class, () -> userDao.addUser(u));
    }

    @Test
    void testPasswordHashing() {
        User user = new User();
        user.setUsername("TestUser");
        user.setPassword("pass");
        user.setEmail("mail@mail.com");

        userDao.addUser(user);
        User userFromDb = userDao.getUserByUsername("TestUser");

        Assertions.assertNotEquals("pass", userFromDb.getPassword());
        Assertions.assertEquals(userDao.passwordHashing("pass"),  userFromDb.getPassword());
        userDao.deleteUser(user.getId());
    }

    @Test
    void testPasswordHashingWorks() {
        String basePass = "password";
        String hashedPass = userDao.passwordHashing(basePass);
        Assertions.assertNotEquals(basePass, hashedPass);
    }

    @Test
    void testMergeUser() {
        User u1 = new User();
        u1.setUsername("TestUser1");
        u1.setPassword("pass1");
        u1.setEmail("testUser@oldMail.com");
        userDao.addUser(u1);

        int userID = userDao.getUserByUsername("TestUser1").getId();

        User u2 = new User();
        u2.setId(userID);
        u2.setEmail("testUser@newMail.com");

        userDao.mergeUser(u2);

        User mergedUser = userDao.getUserById(userID);

        Assertions.assertNotNull(mergedUser.getUsername());
        Assertions.assertNotNull(mergedUser.getPassword());
        Assertions.assertEquals("testUser@newMail.com", mergedUser.getEmail());
        Assertions.assertEquals(userID, mergedUser.getId());
        userDao.deleteUser(u1.getId());
    }

    @Test
    void testMergeUserWithNull() {
        Assertions.assertThrows(Exception.class, () -> userDao.mergeUser(null));
    }

    @Test
    void testEditUpdatesFieldsInDb() {
        User u1 = new User();
        u1.setUsername("TestUser1");
        u1.setPassword("pass1");
        u1.setEmail("funnyMailhihihi@hardcoreMailForProgramers.com");

        userDao.addUser(u1);
        userDao.editUser(u1.getId(), "editedUserName", "editedUserPassword", "editedUserEmail@mail.com");

        User u2 = userDao.getUserByUsername("editedUserName");
        Assertions.assertEquals("editedUserName", u2.getUsername());
        Assertions.assertNotEquals(u1.getUsername(), u2.getUsername());
        Assertions.assertEquals("editedUserEmail@mail.com", u2.getEmail());
        Assertions.assertNotEquals(u1.getEmail(), u2.getEmail());
        Assertions.assertEquals(userDao.passwordHashing("editedUserPassword"), u2.getPassword());
        Assertions.assertNotEquals(userDao.passwordHashing(u1.getPassword()), u2.getPassword());
        Assertions.assertEquals(u1.getId(), u2.getId());
        userDao.deleteUser(u1.getId());
    }

    @Test
    void testCountUsers() {
        int startingCount = Integer.parseInt(String.valueOf(userDao.countUsers()));
        User u1 = new User();
        u1.setUsername("TestUser1"); u1.setPassword("pass1"); u1.setEmail("mail@mail.com");
        userDao.addUser(u1);
        User u2 = new User();
        u2.setUsername("TestUser2"); u2.setPassword("pass2"); u2.setEmail("mail2@mail.com");
        userDao.addUser(u2);

        Assertions.assertEquals(startingCount + 2, userDao.countUsers());
        userDao.deleteUser(u1.getId());
        Assertions.assertEquals(startingCount + 1, userDao.countUsers());
        userDao.deleteUser(u2.getId());
        Assertions.assertEquals(startingCount, userDao.countUsers());
    }

    @Test
    void testAdminDevInitialization() {
        User adminDev = userDao.getUserByUsername("AdminDev");

        Assertions.assertNull(adminDev);

        userDao.initAdminDev();


        Assertions.assertNotNull(userDao.getUserByUsername("AdminDev"));
        Assertions.assertEquals("AdminDev", userDao.getUserByUsername("AdminDev").getUsername());
        Assertions.assertEquals(userDao.passwordHashing("admindev"), userDao.getUserByUsername("AdminDev").getPassword());
        Assertions.assertEquals("admin@example.com", userDao.getUserByUsername("AdminDev").getEmail());
    }




}
