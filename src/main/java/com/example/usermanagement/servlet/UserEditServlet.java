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

@WebServlet("/userEdit")
public class UserEditServlet extends HttpServlet {
    private UserDao userDao = new UserDao();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            User user = userDao.getUserById(id);
            req.setAttribute("user", user);
            req.getRequestDispatcher("pages/userEdit.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User current = (User) req.getSession().getAttribute("user");
        if (!PermissionUtil.hasPermission(current, "EDIT_USER")) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Not authorized to edit users");
            return;
        }

        String idParam = req.getParameter("id");
        String userNameParam = req.getParameter("username");
        String passwordParam = req.getParameter("password");
        String emailParam = req.getParameter("email");

        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            userDao.editUser(id, userNameParam, passwordParam, emailParam);
        }

        resp.sendRedirect(req.getContextPath() + "/users");
    }
}

