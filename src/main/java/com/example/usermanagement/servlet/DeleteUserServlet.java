package com.example.usermanagement.servlet;


import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.dbutil.PermissionUtil;
import com.example.usermanagement.logs.ActionType;
import com.example.usermanagement.logs.Logger;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/delete")
public class DeleteUserServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // tylko dla zalogowanych i z DELETE_USER
        User current = (User) req.getSession().getAttribute("user");
        if (!PermissionUtil.hasPermission(current, "DELETE_USER")) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Not authorized to delete users");
            return;
        }

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/users");
            return;
        }

        int id = Integer.parseInt(idParam);
        User target = userDao.getUserById(id);
        if (target != null && "ROLE_ADMINDEVELOPER".equals(target.getRole().getName())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Cannot delete admin developer role user");
            return;
        }

        userDao.deleteUser(id);

        Logger.log(current.getUsername(), target.getUsername(), ActionType.DELETE_USER);

        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
