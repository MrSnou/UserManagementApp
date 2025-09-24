package com.example.usermanagement.servlet;


import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.dbutil.PermissionUtil;
import com.example.usermanagement.logs.ActionType;
import com.example.usermanagement.logs.Logger;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User current = (User) req.getSession().getAttribute("user");
        if (!PermissionUtil.hasPermission(current, "EDIT_USER")) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Not authorized to edit users");
            return;
        }

        String idParam = req.getParameter("id");

        User user = userDao.getUserById(Integer.parseInt(idParam));
        if ("ROLE_ADMINDEVELOPER".equals(user.getRole().getName())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Cannot change data of admin developer");
            return;
        }

        String userNameParam = req.getParameter("username");
        String passwordParam = req.getParameter("password");
        String emailParam = req.getParameter("email");

        if (userNameParam == null || !userNameParam.matches("^[A-Za-z0-9]+$")) {
            req.setAttribute("usernameError", "Username can only contain letters and numbers (A-Z, a-z, 0-9).");
            req.setAttribute("user", user); // żeby formularz miał dane
            req.getRequestDispatcher("pages/userEdit.jsp").forward(req, resp);
            return;
        }

        if (passwordParam == null || !passwordParam.matches("^[A-Za-z0-9!@#$^&*()_\\-+=]+$")) {
            req.setAttribute("passwordError", "Password can only contain letters and numbers (A-Z, a-z, 0-9).");
            req.setAttribute("user", user);
            req.getRequestDispatcher("pages/userEdit.jsp").forward(req, resp);
            return;
        }

        if (emailParam == null || !emailParam.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            req.setAttribute("emailError", "Invalid email format.");
            req.setAttribute("user", user);
            req.getRequestDispatcher("pages/userEdit.jsp").forward(req, resp);
            return;
        }

        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            userDao.editUser(id, userNameParam, passwordParam, emailParam);
        }

        Logger.log(current.getUsername(), userDao.getUserById(Integer.parseInt(idParam)).getUsername(), ActionType.EDIT_USERDATA);

        resp.sendRedirect(req.getContextPath() + "/users");
    }
}

