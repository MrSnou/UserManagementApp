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

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static UserDao userDao = new UserDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try  {
            User current =  (User) request.getSession().getAttribute("user");
            Logger.log(current.getUsername(),null, ActionType.LOGOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getSession().invalidate();
        response.sendRedirect((request.getContextPath() + "index.jsp"));
    }
}
