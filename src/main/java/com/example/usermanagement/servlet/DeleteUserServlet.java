package com.example.usermanagement.servlet;


import com.example.usermanagement.dao.UserDao;
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
    protected void doGet(HttpServletRequest req,  HttpServletResponse resp) throws IOException{
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/users");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            userDao.deleteUser(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
