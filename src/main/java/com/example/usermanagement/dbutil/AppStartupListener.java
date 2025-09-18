package com.example.usermanagement.dbutil;

import com.example.usermanagement.dao.UserDao;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppStartupListener implements ServletContextListener {
    UserDao userDao = new UserDao();
    @Override
    public void contextInitialized(ServletContextEvent event) {
        userDao.initAdminDev();
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
