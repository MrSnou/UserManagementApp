package com.example.usermanagement.servlet;

import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.dbutil.PermissionUtil;
import com.example.usermanagement.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserListServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int PAGE_SIZE = 10;
        int page = 1;
        int pages = (userDao.getUsers().size() / PAGE_SIZE) + 1;

        String pageParam = req.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page > pages ||  page < 1) {
                    req.setAttribute("errorMessage", "Invalid page number");
                    page = 1;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("errorMessage", "Number of site must be a positive integer.");
                page = 1;
            }
        }
        List<User> users = userDao.getUsersPaged(page, PAGE_SIZE);
        int totalUsers = (int) userDao.countUsers();
        int totalPages = (int) Math.ceil((double) totalUsers / PAGE_SIZE);

        req.setAttribute("users", users);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        User sessionUser = (User) req.getSession().getAttribute("user");

        boolean canEdit = PermissionUtil.hasPermission(sessionUser, "EDIT_USER");
        boolean canDelete = PermissionUtil.hasPermission(sessionUser, "DELETE_USER");
        boolean canChangeRole = PermissionUtil.hasPermission(sessionUser, "CHANGE_ROLE");

        req.setAttribute("canEdit", canEdit);
        req.setAttribute("canDelete", canDelete);
        req.setAttribute("canChangeRole", canChangeRole);

        req.getRequestDispatcher("/pages/users.jsp").forward(req, resp);
    }
}
