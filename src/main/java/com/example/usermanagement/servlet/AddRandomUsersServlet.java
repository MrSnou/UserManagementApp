package com.example.usermanagement.servlet;


import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.logs.ActionType;
import com.example.usermanagement.logs.Logger;
import com.example.usermanagement.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/addRandomUsers")
public class AddRandomUsersServlet extends HttpServlet {
    private UserDao userDao = new  UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String countParam = req.getParameter("count");
        int count = 1;
        if (countParam != null) {
            try {
                count = Integer.parseInt(countParam);
            } catch (NumberFormatException e) {
                count = 1;
            }

            User current = (User) req.getSession().getAttribute("user");
            Logger.log(current.getUsername(), String.valueOf(count), ActionType.ADD_RANDOM_USERS);

            userDao.addRandomUsers(count);

            resp.sendRedirect(req.getContextPath() + "/users");
        }
    }
}
