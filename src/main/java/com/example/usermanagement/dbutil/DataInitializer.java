package com.example.usermanagement.dbutil;

import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.model.Permission;
import com.example.usermanagement.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DataInitializer {

    public static void init(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();


            Permission view = session.createQuery("FROM Permission WHERE name = 'VIEW_DB'", Permission.class).uniqueResult();
            if (view == null) {
                view = new Permission("VIEW_DB", "Can view user list");
                session.persist(view);
            }

            Permission edit = session.createQuery("FROM Permission WHERE name = 'EDIT_USER'", Permission.class).uniqueResult();
            if (edit == null) {
                edit = new Permission("EDIT_USER", "Can edit users");
                session.persist(edit);
            }

            Permission del = session.createQuery("FROM Permission WHERE name = 'DELETE_USER'", Permission.class).uniqueResult();
            if (del == null) {
                del = new Permission("DELETE_USER", "Can delete users");
                session.persist(del);
            }

            Permission change = session.createQuery("FROM Permission WHERE name = 'CHANGE_ROLE'", Permission.class).uniqueResult();
            if (change == null) {
                change = new Permission("CHANGE_ROLE", "Can change user roles");
                session.persist(change);
            }

            Role userRole = session.createQuery("FROM Role WHERE name = 'ROLE_USER'", Role.class).uniqueResult();
            if (userRole == null) {
                userRole = new Role("ROLE_USER");
                userRole.getPermissions().add(view);
                userRole.getPermissions().add(edit);
                session.persist(userRole);
            } else {
                userRole.getPermissions().add(view);
                userRole.getPermissions().add(edit);
                session.merge(userRole);
            }

            Role adminRole = session.createQuery("FROM Role WHERE name = 'ROLE_ADMIN'", Role.class).uniqueResult();
            if (adminRole == null) {
                adminRole = new Role("ROLE_ADMIN");
                adminRole.getPermissions().add(view);
                adminRole.getPermissions().add(edit);
                adminRole.getPermissions().add(del);
                adminRole.getPermissions().add(change);
                session.persist(adminRole);
            } else {
                adminRole.getPermissions().add(view);
                adminRole.getPermissions().add(edit);
                adminRole.getPermissions().add(del);
                adminRole.getPermissions().add(change);
                session.merge(adminRole);
            }

            Role adminDevRole = session.createQuery("FROM Role WHERE name = 'ROLE_ADMINDEVELOPER'", Role.class).uniqueResult();
            if (adminDevRole == null) {
                adminDevRole = new Role("ROLE_ADMINDEVELOPER");
                adminDevRole.getPermissions().add(view);
                adminDevRole.getPermissions().add(edit);
                adminDevRole.getPermissions().add(del);
                adminDevRole.getPermissions().add(change);
                session.persist(adminDevRole);
            } else {
                adminDevRole.getPermissions().add(view);
                adminDevRole.getPermissions().add(edit);
                adminDevRole.getPermissions().add(del);
                adminDevRole.getPermissions().add(change);
                session.merge(adminDevRole);
            }

            tx.commit();
        }
    }
}
