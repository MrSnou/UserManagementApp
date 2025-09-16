package com.example.usermanagement.dao;

import com.example.usermanagement.dbutil.HibernateUtil;
import com.example.usermanagement.model.Permission;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PermissionDao {

    public Permission getByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Permission WHERE name = :name", Permission.class)
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }

    public void save(Permission permission) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(permission);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
                throw ex;
            }
        }
    }
}
