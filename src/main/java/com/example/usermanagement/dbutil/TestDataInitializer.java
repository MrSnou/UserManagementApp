package com.example.usermanagement.dbutil;

import com.example.usermanagement.model.Permission;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TestDataInitializer {
    public static void init(SessionFactory sf) {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();

            // Permissions
            Permission view = new Permission("VIEW_DB", "Can view user list");
            Permission edit = new Permission("EDIT_USER", "Can edit users");
            Permission del = new Permission("DELETE_USER", "Can delete users");
            Permission change = new Permission("CHANGE_ROLE", "Can change user roles");
            Permission logs = new Permission("VIEW_LOGS", "Can view logs");

            session.persist(view);
            session.persist(edit);
            session.persist(del);
            session.persist(change);
            session.persist(logs);

            // Roles
            Role userRole = new Role("ROLE_USER");
            userRole.getPermissions().add(view);
            session.persist(userRole);

            Role adminRole = new Role("ROLE_ADMIN");
            adminRole.getPermissions().add(view);
            adminRole.getPermissions().add(edit);
            adminRole.getPermissions().add(del);
            adminRole.getPermissions().add(logs);
            session.persist(adminRole);

            Role adminDevRole = new Role("ROLE_ADMINDEVELOPER");
            adminDevRole.getPermissions().add(view);
            adminDevRole.getPermissions().add(edit);
            adminDevRole.getPermissions().add(del);
            adminDevRole.getPermissions().add(change);
            adminDevRole.getPermissions().add(logs);
            session.persist(adminDevRole);

            tx.commit();
        }
    }
}
