package com.dineease.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/dineease";

    private static final String USER = "root";

    private static final String PASSWORD = "#Sapna@2007";

    private static Connection connection;

    private DBConnection() {
    }

    public static Connection getConnection() {

        try {

            if (connection == null || connection.isClosed()) {

                connection = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return connection;
    }
}