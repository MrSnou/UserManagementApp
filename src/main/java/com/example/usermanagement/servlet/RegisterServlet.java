package com.example.usermanagement.servlet;

import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                email == null || email.isEmpty()) {
            req.setAttribute("error", "Please fill all the fields");
            req.getRequestDispatcher("pages/register.jsp").forward(req, resp);
            return;
        }
        if (!email.contains("@")) {
            req.setAttribute("error", "Email is not valid");
            req.getRequestDispatcher("pages/register.jsp").forward(req, resp);
            return;
        }

        boolean usernameExist = false;
        boolean emailExist = false;

        for (User u : userDao.getUsers()) {
            if (u.getUserName().equals(username)) {
                usernameExist = true;
                break;
            }
            if (u.getEmail().equals(email)) {
                emailExist = true;
                break;
            }
        }

        if (usernameExist) {
            req.setAttribute("error", "User already exists!");
            req.getRequestDispatcher("pages/register.jsp").forward(req, resp);
            return;
        }
        if (emailExist) {
            req.setAttribute("error", "User email already exists!");
            req.getRequestDispatcher("pages/register.jsp").forward(req, resp);
            return;
        }



        User user = new User();
        user.setUserName(username);

        user.setPassword(password);
        user.setEmail(email);

        userDao.addUser(user);

        resp.sendRedirect("/users");
    }
}
