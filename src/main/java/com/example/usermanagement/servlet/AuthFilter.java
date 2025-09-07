package com.example.usermanagement.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter({"/secure/*", "/delete", "/userEdit"})  // chronimy wszystkie adresy zaczynające się od /secure/
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Sprawdź czy w sesji jest użytkownik
        Object user = req.getSession().getAttribute("user");

        if (user == null) {
            // brak zalogowanego użytkownika → przekierowanie do logowania
            resp.sendRedirect(req.getContextPath() + "/pages/login.jsp");
        } else {
            // zalogowany → przepuszczamy dalej
            chain.doFilter(request, response);
        }
    }
}
