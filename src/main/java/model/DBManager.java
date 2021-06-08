package model;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class DBManager {
    public static boolean isTest = false;
    public static BasicDataSource dataSource = new BasicDataSource();

    public static DBManager dbManager;

    private DBManager() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("java\\model", "resources");
        String appConfigPath;
        if(isTest) {
            appConfigPath = rootPath + "\\testDatabase";
        } else {
            appConfigPath = rootPath + "\\database";
        }
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
            dataSource.setUrl(appProps.getProperty("CONNECTION_URL"));
            dataSource.setUsername(appProps.getProperty("USER"));
            dataSource.setPassword(appProps.getProperty("PASSWORD"));
            dataSource.setDriverClassName(appProps.getProperty("DRIVER"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();

        }
        return dbManager;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}