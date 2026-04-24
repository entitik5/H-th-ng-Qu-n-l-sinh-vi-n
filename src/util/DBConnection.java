package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private DBConnection() {
        try {
            Properties props = loadProperties();
            String host     = props.getProperty("db.host",     "localhost");
            String port     = props.getProperty("db.port",     "3306");
            String dbName   = props.getProperty("db.name",     "qlsv");
            String username = props.getProperty("db.username", "root");
            String password = props.getProperty("db.password", "");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                + "?useSSL=false&serverTimezone=UTC"
                + "&characterEncoding=UTF-8&allowPublicKeyRetrieval=true";

            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Ket noi DataBase thanh cong!");

        } catch (ClassNotFoundException e) {
            System.err.println("Khong tim thay MySQL Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Ket noi DataBase that bai!");
            e.printStackTrace();
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || !connection.isValid(3)) {
                System.out.println("Mat ket noi, dang ket noi lai...");
                Properties props = loadProperties();
                String host     = props.getProperty("db.host",     "localhost");
                String port     = props.getProperty("db.port",     "3306");
                String dbName   = props.getProperty("db.name",     "qlsv");
                String username = props.getProperty("db.username", "root");
                String password = props.getProperty("db.password", "");

                String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                    + "?useSSL=false&serverTimezone=UTC"
                    + "&characterEncoding=UTF-8&allowPublicKeyRetrieval=true";

                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Ket noi lai thanh cong!");
            }
        } catch (SQLException e) {
            System.err.println("Khong the ket noi lai database!");
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Da dong ket noi database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream in = DBConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (in != null) {
                props.load(in);
                return props;
            }
        } catch (IOException e) {
            System.err.println("Khong doc duoc db.properties tu classpath");
        }
        try (InputStream in = new java.io.FileInputStream("db.properties")) {
            props.load(in);
            return props;
        } catch (IOException e) {
            System.err.println("Khong tim thay db.properties! Dung gia tri mac dinh.");
        }
        return props;
    }

    public static void main(String[] args) {
        Connection conn = DBConnection.getInstance().getConnection();
        if (conn != null) 
            System.out.println("Test ket noi thanh cong!");
        else              
            System.out.println("Test ket noi that bai.");
        DBConnection.getInstance().closeConnection();
    }
}