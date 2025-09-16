package com.example.usermanagement.dao;

import com.example.usermanagement.dbutil.HibernateUtil;
import com.example.usermanagement.model.Permission;
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
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(role);
            tx.commit();
        } catch (Exception e) {
            if  (tx != null) {
                tx.rollback();
                throw e;
            }
        }
    }
    public void addPermissionToRole(String roleName, String permissionName) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Role role = session.createQuery("FROM Role WHERE name = :name", Role.class)
                    .setParameter("name", roleName)
                    .uniqueResult();
            Permission permission = session.createQuery("FROM Permission WHERE name = :p",  Permission.class)
                    .setParameter("p", permissionName)
                    .uniqueResult();

            if (role == null) throw new RuntimeException("Role not found: " + roleName);
            if (permission == null) throw new RuntimeException("Permission not found: " + permissionName);

            role.getPermissions().add(permission);
            session.merge(role);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        }
    }
}
