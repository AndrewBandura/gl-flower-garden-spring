package com.flowergarden.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Andrew Bandura
 */
public class ConnectionFactory {

    private static String url;
    private String mode;

    public ConnectionFactory(String mode) {
        this.mode = mode;
        this.url = getUrl(this.mode);
    }

    private static String getUrl(String mode) {

        String dbPath = "";
        Properties prop = new Property().getProperties();

        if(mode == "prod")
            dbPath = prop.getProperty("db");
        else if(mode=="test")
            dbPath = prop.getProperty("db_test");

        File file = new File(dbPath);

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
            return DriverManager.getConnection(url);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

}
