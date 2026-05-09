package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class Databaseconnection {

    private static final String url = "jdbc:mysql://localhost:3306/kasynodb?useUnicode=true&characterEncoding=UTF-8";
    private static final String user = "root";
    private static final String password = "";

    public static Connection getConnection() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(url, user, password);
    }
}