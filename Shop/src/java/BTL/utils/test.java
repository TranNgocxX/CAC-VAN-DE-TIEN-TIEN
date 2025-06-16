package BTL.utils;

public class test {
    public static void main(String[] args) {
        DBConnection db = DBConnection.getInstance();
        
        if (db.getConnection() != null) {
            System.out.println("Kết nối CSDL thành công!");
        } else {
            System.out.println("Kết nối CSDL thất bại!");
        }

        // Đóng kết nối sau khi kiểm tra
        db.closeConnection();
    }
}
