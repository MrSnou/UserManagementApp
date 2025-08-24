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
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);

        userDao.addUser(user);

        resp.sendRedirect("/users");
    }
}
