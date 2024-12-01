package web.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

// db 연결 부분 처리하는 클래스
public class DatabaseUtil {
    private static String JDBC_DRIVER;
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    static {
        try {
            Properties props = new Properties();
            InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("db.properties");
            props.load(input);

            JDBC_DRIVER = props.getProperty("jdbc.driver");
            DB_URL = props.getProperty("jdbc.url");
            DB_USER = props.getProperty("jdbc.username");
            DB_PASSWORD = props.getProperty("jdbc.password");

            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}

