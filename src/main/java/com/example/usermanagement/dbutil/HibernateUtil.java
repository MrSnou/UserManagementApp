package com.example.usermanagement.dbutil;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration cfg = new Configuration().configure();

            String jdbcUrl = System.getenv("JDBC_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPass = System.getenv("DB_PASSWORD");
            String hDialect = System.getenv("HIBERNATE_DIALECT");

            if (jdbcUrl != null) {
                cfg.setProperty("hibernate.connection.url", jdbcUrl);
            }
            if (dbUser != null) {
                cfg.setProperty("hibernate.connection.username", dbUser);
            }
            if (dbPass != null) {
                cfg.setProperty("hibernate.connection.password", dbPass);
            }
            if (hDialect != null) {
                cfg.setProperty("hibernate.dialect", hDialect);
            }

            SessionFactory factory = cfg.buildSessionFactory();

            DataInitializer.init(factory);

            return factory;
        }  catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void  shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("[HibernateUtil] SessionFactory closed.");
        }
    }
}
