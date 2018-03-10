package com.flowergarden.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Andrew Bandura
 */
public class ConnectionFactory {

    public static File file = new File("flowergarden.db");
    public static final String URL = getUrl();


    private static String getUrl() {

        try {
            return "jdbc:sqlite:"+file.getCanonicalFile().toURI();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static Connection getConnection()
    {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

}
