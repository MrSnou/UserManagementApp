package com.example.usermanagement.dao;

import com.example.usermanagement.dbutil.HibernateUtil;
import com.example.usermanagement.dbutil.TestDataInitializer;
import com.example.usermanagement.dbutil.TestHibernateUtil;
import com.example.usermanagement.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

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
    void testGetUserByUsername() {
        User u = new User();
        u.setUsername("Alice");
        u.setPassword("pass");
        u.setEmail("alice@example.com");

        userDao.addUser(u);

        User actualUser = userDao.getUserByUsername("Alice");
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals("Alice", actualUser.getUsername());
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
    }
}
