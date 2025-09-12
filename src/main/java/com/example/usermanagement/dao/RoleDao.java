package com.example.usermanagement.dao;

import com.example.usermanagement.dbutil.HibernateUtil;
import com.example.usermanagement.model.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RoleDao {
    public Role getRoleByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Role WHERE name = :name", Role.class)
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }

    public void save(Role role) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(role);
            tx.commit();
        }
    }
}
