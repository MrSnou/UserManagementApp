package com.example.usermanagement.servlet;

import com.example.usermanagement.dao.RoleDao;
import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.logs.ActionType;
import com.example.usermanagement.logs.Logger;
import com.example.usermanagement.model.Role;
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
    private RoleDao roleDao = new RoleDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/register.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username"); // TO-DO - Regex
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if (username == null || !username.matches("^[A-Za-z0-9]+$") || username.length() > 32) {
            req.setAttribute("usernameError", "Username can only contain letters, numbers (A-Z, a-z, 0-9) and have to be shorter than 32 characters.");
            req.getRequestDispatcher("pages/register.jsp").forward(req, resp);
            return;
        }

        if (password == null || !password.matches("^[A-Za-z0-9!@#$^&*()_\\-+=]+$")) {
            req.setAttribute("passwordError", "Password can only contain letters and numbers (A-Z, a-z, 0-9).");
            req.getRequestDispatcher("pages/register.jsp").forward(req, resp);
            return;
        }

        if (email == null || email.length() > 64) {
            req.setAttribute("emailError", "Email can't be longer than 64 characters.");
            req.getRequestDispatcher("pages/register.jsp").forward(req, resp);
            return;
        }

        if (!email.contains("@")) {
            req.setAttribute("error", "Email is not valid");
            req.getRequestDispatcher("pages/register.jsp").forward(req, resp);
            return;
        }

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            req.setAttribute("emailError", "Invalid email format.");
            req.getRequestDispatcher("pages/register.jsp").forward(req, resp);
            return;
        }

        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                email == null || email.isEmpty()) {
            req.setAttribute("error", "Please fill all the fields");
            req.getRequestDispatcher("pages/register.jsp").forward(req, resp);
            return;
        }

        boolean usernameExist = false;
        boolean emailExist = false;

        for (User u : userDao.getUsers()) {
            if (u.getUsername().equals(username)) {
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
        user.setUsername(username);

        user.setPassword(password);
        user.setEmail(email);

        Role userRole = roleDao.getRoleByName("ROLE_USER");
        if (userRole == null) {
            throw new RuntimeException("Default role ROLE_USER not found in database!");
        }
        user.setRole(userRole);

        userDao.addUser(user);

        Logger.log(user.getUsername(), null, ActionType.CREATE_USER);

        resp.sendRedirect("/");
    }
}
