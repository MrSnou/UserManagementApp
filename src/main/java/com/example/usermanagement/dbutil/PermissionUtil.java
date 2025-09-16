package com.example.usermanagement.dbutil;

import com.example.usermanagement.model.Permission;
import com.example.usermanagement.model.User;

public class PermissionUtil {

    public static boolean hasPermission(User user, String permissionName) {
        if (user == null || user.getRole() == null) return false;
        for (Permission p : user.getRole().getPermissions()) {
            if (permissionName.equals(p.getName())) return true;
        }
        return false;
    }
}
