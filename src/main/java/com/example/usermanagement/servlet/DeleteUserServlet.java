package com.example.usermanagement.servlet;


import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.model.Role;
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
        User user = userDao.getUserById(Integer.parseInt(idParam));

        if (idParam == null || idParam.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/users");
            return;
        }

        if (user.getRole() == null) {
            user.setRole(new Role("ROLE_USER"));
        }


        if ("ROLE_ADMINDEVELOPER".equals(user.getRole().getName())) {
            throw new UnsupportedOperationException("Cannot delete ROLE_ADMINDEVELOPER!");
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
