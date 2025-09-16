package com.example.usermanagement.servlet;

import com.example.usermanagement.dao.RoleDao;
import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.dbutil.PermissionUtil;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/changeRole")
public class ChangeRoleServlet extends HttpServlet {
    private UserDao userDao = new UserDao();
    private RoleDao roleDao = new RoleDao();

    @Override
    protected void doPost(HttpServletRequest req,  HttpServletResponse resp) throws ServletException, IOException {
        User current = (User) req.getSession().getAttribute("user");
        if (!PermissionUtil.hasPermission(current, "CHANGE_ROLE")) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        int id = Integer.parseInt(req.getParameter("id"));
        String roleName = req.getParameter("role");

        Role newRole = roleDao.getRoleByName(roleName);
        if (newRole == null) {
            req.setAttribute("error", "Role not found");
            req.getRequestDispatcher("/pages/users.jsp").forward(req, resp);
            return;
        }

        User user = userDao.getUserById(id);
        user.setRole(newRole);
        userDao.mergeUser(user);
        resp.sendRedirect(req.getContextPath() + "/users");
    }

}
