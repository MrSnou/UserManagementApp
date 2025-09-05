package com.example.usermanagement.servlet;

import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password").trim();


        if  (username == null || password == null || username.isEmpty() || password.isEmpty()) {
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
                req.getSession().setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/home");
            } else {
                req.setAttribute("error", "Wrong password.");
                req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
                System.out.println("DB password=" + user.getPassword());
                System.out.println("Entered password hash=" + hashedPassword);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("error", "Unexpected error: " + ex.getMessage());
            req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
        }

    }
}
