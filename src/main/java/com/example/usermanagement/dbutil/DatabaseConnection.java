package com.example.usermanagement.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
                            /** Legacy Code **/
    /** Whole code refactored to Hibernate, no need for handwriting in SQL **/
//    private static final String URL = "jdbc:h2:C:/Users/jakub/IdeaProjects/UserManagementApp/testdb;AUTO_SERVER=TRUE";
//    private static final String USER = "sa";
//    private static final String PASSWORD = "sa";
//
//    static {
//        try {
//            Class.forName("org.h2.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException("H2 Driver not found!", e);
//        }
//        try {
//            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
//
//            Statement stmt = connection.createStatement();
//            stmt.execute("CREATE TABLE IF NOT EXISTS users(" +
//                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
//                    "username VARCHAR(255), " +
//                    "email VARCHAR(255), " +
//                    "password VARCHAR(255))");
//
//            stmt.close();
//            connection.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }

}
