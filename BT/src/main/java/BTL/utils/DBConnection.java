package BTL.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private final String url = "jdbc:mysql://localhost:3306/BTL?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Ho_Chi_Minh";
    private final String username = "root";
    private final String password = "tranngoc2004";

    private DBConnection() {
        ketNoi();
    }

    private void ketNoi() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Kết nối database thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Kết nối database thất bại!");
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
            if (connection == null || connection.isClosed()) {
                System.out.println("🔁 Kết nối bị đóng, tạo lại...");
                ketNoi();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
