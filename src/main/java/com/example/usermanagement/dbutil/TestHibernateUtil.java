package com.example.usermanagement.dbutil;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TestHibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate-test.cfg.xml");
            return cfg.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial Test SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() { return sessionFactory; }

    public static void shutdown() {
        if (sessionFactory != null) sessionFactory.close();
    }
}
