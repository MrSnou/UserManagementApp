package com.example.usermanagement.servlet;

import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.dbutil.PermissionUtil;
import com.example.usermanagement.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserListServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userDao.getUsers();
        req.setAttribute("users", users);

        User sessionUser = (User) req.getSession().getAttribute("user");

        boolean canEdit = PermissionUtil.hasPermission(sessionUser, "EDIT_USER");
        boolean canDelete = PermissionUtil.hasPermission(sessionUser, "DELETE_USER");
        boolean canChangeRole = PermissionUtil.hasPermission(sessionUser, "CHANGE_ROLE");

        req.setAttribute("canEdit", canEdit);
        req.setAttribute("canDelete", canDelete);
        req.setAttribute("canChangeRole", canChangeRole);

        req.getRequestDispatcher("/pages/users.jsp").forward(req, resp);
    }
}
