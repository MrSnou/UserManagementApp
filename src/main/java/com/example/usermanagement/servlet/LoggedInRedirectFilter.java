package com.example.usermanagement.servlet;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter({"/login", "/register"})  // chronimy logowanie i rejestrację
public class LoggedInRedirectFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Object user = req.getSession().getAttribute("user");

        if (user != null) {
            // jeśli już zalogowany → przekierowanie na stronę domową
            resp.sendRedirect(req.getContextPath() + "/pages/home.jsp");
        } else {
            // jeśli nie zalogowany → normalnie obsługuj dalej (servlet/jsp)
            chain.doFilter(request, response);
        }
    }
}

