package BTL.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;
    
private DBConnection() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Đã load driver MySQL");
        
        String url = "jdbc:mysql://localhost:3306/BTL?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "tranngoc2004";

        connection = DriverManager.getConnection(url, username, password);
        System.out.println("Kết nối database thành công!");
    } catch (ClassNotFoundException e) {
        System.out.println("Không tìm thấy driver MySQL!");
        e.printStackTrace();
    } catch (SQLException e) {
        System.out.println("Kết nối database thất bại!");
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
        return connection;
    }
    
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                instance = null;
                System.out.println("Đóng kết nối database thành công!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}