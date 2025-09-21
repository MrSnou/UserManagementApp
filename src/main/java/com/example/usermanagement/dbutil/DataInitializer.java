package com.example.usermanagement.dbutil;

import com.example.usermanagement.model.Permission;
import com.example.usermanagement.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.Set;

public class DataInitializer {

    public static void init(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            // permissions
            Permission view = getOrCreatePermission(session, "VIEW_DB", "Can view user list");
            Permission edit = getOrCreatePermission(session, "EDIT_USER", "Can edit users");
            Permission del = getOrCreatePermission(session, "DELETE_USER", "Can delete users");
            Permission change = getOrCreatePermission(session, "CHANGE_ROLE", "Can change user roles");
            Permission logs = getOrCreatePermission(session, "VIEW_LOGS", "Can view server logs");

            // roles (ustawiamy dokładnie wymaganą listę permisji)
            getOrCreateOrUpdateRole(session, "ROLE_USER", setOf(view));
            getOrCreateOrUpdateRole(session, "ROLE_ADMIN", setOf(view, edit, del, logs));
            getOrCreateOrUpdateRole(session, "ROLE_ADMINDEVELOPER", setOf(view, edit, del, change, logs));

            tx.commit();
        }
    }

    private static Permission getOrCreatePermission(Session session, String name, String description) {
        Permission p = session.createQuery("FROM Permission WHERE name = :name", Permission.class)
                .setParameter("name", name)
                .uniqueResult();
        if (p == null) {
            p = new Permission(name, description);
            session.persist(p);
        } else {
            // opcjonalnie: aktualizuj description
            p.setDescription(description);
            session.merge(p);
        }
        return p;
    }

    private static Role getOrCreateOrUpdateRole(Session session, String roleName, Set<Permission> permissions) {
        Role role = session.createQuery("FROM Role WHERE name = :name", Role.class)
                .setParameter("name", roleName)
                .uniqueResult();
        if (role == null) {
            role = new Role(roleName);
            role.setPermissions(new HashSet<>(permissions));
            session.persist(role);
        } else {
            // wyczyść stare i ustaw dokładnie taki zestaw permisji, jaki chcemy
            role.getPermissions().clear();
            role.getPermissions().addAll(permissions);
            session.merge(role);
        }
        return role;
    }

    // mała pomocnicza metoda do wygodnego tworzenia setów
    @SafeVarargs
    private static <T> Set<T> setOf(T... items) {
        Set<T> s = new HashSet<>();
        for (T t : items) s.add(t);
        return s;
    }
}