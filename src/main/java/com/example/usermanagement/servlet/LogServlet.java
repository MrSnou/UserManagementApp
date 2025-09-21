package com.example.usermanagement.servlet;

import com.example.usermanagement.dbutil.PermissionUtil;
import com.example.usermanagement.logs.LogEntry;
import com.example.usermanagement.logs.Logger;
import com.example.usermanagement.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet("/logs")
public class LogServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User current = (User)request.getSession().getAttribute("user");

        if (current == null || !PermissionUtil.hasPermission(current, "VIEW_LOGS")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not authorized to view this page");
            return;
        }

        List<LogEntry> allLogs = Logger.getLogsList();
        request.setAttribute("logs", allLogs);

        request.getRequestDispatcher("/pages/logs.jsp").forward(request, response);
    }
}
