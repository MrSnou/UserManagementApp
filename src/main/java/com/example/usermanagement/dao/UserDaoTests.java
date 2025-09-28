package com.example.usermanagement.dao;

import com.example.usermanagement.dbutil.HibernateUtil;
import com.example.usermanagement.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class UserDaoTests {
    private UserDao userDao;
    private Session sessionMock;

    @BeforeEach
    public void setUp() {
        userDao = new UserDao();
        sessionMock = HibernateUtil.getSessionFactory().openSession();
    }

    @Test
    void testGetUserByUsername() {
        User expectedUser = new User();
        expectedUser.setUsername("Alice");

        Query<User> queryMock = Mockito.mock(Query.class);
        when(sessionMock.createQuery("FROM User WHERE username = :username", User.class)).thenReturn(queryMock);
        when(queryMock.setParameter("username", expectedUser.getUsername())).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(expectedUser);

        User actualUser = userDao.getUserByUsername("Alice");
        Assertions.assertEquals(expectedUser, actualUser);
    }
}
