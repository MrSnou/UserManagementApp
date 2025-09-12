package com.example.usermanagement.dbutil;

import com.example.usermanagement.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DataInitializer {

    public static void init(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            if (session.createQuery("FROM Role WHERE name = 'ROLE_USER'", Role.class).uniqueResult() == null) {
                session.persist(new Role("ROLE_USER"));
            }

            if  (session.createQuery("FROM Role WHERE name = 'ROLE_ADMIN'", Role.class).uniqueResult() == null) {
                session.persist(new Role("ROLE_ADMIN"));
            }

            if (session.createQuery("FROM Role WHERE name = 'ROLE_ADMINDEVELOPER'", Role.class).uniqueResult() == null) {
                session.persist(new Role("ROLE_ADMINDEVELOPER"));
            }

            tx.commit();
        }
    }
}
