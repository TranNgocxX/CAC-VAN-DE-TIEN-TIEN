package BTL.daos;

import BTL.models.LoaiSanPham;
import BTL.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoaiSanPhamDAO {
    private Connection connection;

    public LoaiSanPhamDAO() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public List<LoaiSanPham> getAllLoaiSanPham() throws SQLException {
        List<LoaiSanPham> loaiSanPhams = new ArrayList<>();
        String sql = "SELECT * FROM loai_san_pham";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                LoaiSanPham loai = new LoaiSanPham();
                loai.setId(rs.getInt("id"));
                loai.setTenLoai(rs.getString("ten_loai"));
                loaiSanPhams.add(loai);
            }
        }
        return loaiSanPhams;
    }
}