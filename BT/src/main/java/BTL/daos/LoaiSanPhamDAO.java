package BTL.daos;

import BTL.models.LoaiSanPham;
import BTL.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiSanPhamDAO {
    public List<LoaiSanPham> layTatCa() {
        List<LoaiSanPham> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM loai_san_pham";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LoaiSanPham loai = new LoaiSanPham(
                        rs.getInt("id"),
                        rs.getString("ten_loai")
                );
                danhSach.add(loai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSach;
    }
}