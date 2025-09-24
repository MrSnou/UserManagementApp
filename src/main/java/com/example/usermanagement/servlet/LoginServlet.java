package com.example.usermanagement.servlet;

import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.logs.ActionType;
import com.example.usermanagement.logs.Logger;
import com.example.usermanagement.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password").trim();


        if  (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            req.setAttribute("error", "Write correct username and password.");
            req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
            return;
        }

        try {
            User user = userDao.getUserByUsername(username);
            if (user == null) {
                req.setAttribute("error", "User not found.");
                req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
                return;
            }

            String hashedPassword = userDao.passwordHashing(password).trim();
            if (hashedPassword.equals(user.getPassword())) {

                HttpSession old = req.getSession(false);
                if (old != null) old.invalidate();
                HttpSession session = req.getSession(true);

                session.setAttribute("user", user);

                Logger.log(user.getUsername(), null, ActionType.LOGIN);

                resp.sendRedirect(req.getContextPath() + "/pages/home.jsp");

            } else {
                req.setAttribute("error", "Wrong password.");
                Logger.log(user.getUsername(),null, ActionType.LOGIN_FAILURE_WRONG_PASS);
                req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("error", "Unexpected error occurred. Please try again later.");
            ex.printStackTrace();
            req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
        }

    }
}
