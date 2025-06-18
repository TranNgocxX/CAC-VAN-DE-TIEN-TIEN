package BTL.daos;

import BTL.models.BinhLuan;
import BTL.models.NguoiDung;
import BTL.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BinhLuanDAO {
    private Connection connection;

    public BinhLuanDAO() {
        connection = DBConnection.getInstance().getConnection();
    }
    public List<BinhLuan> layTheoSanPham(int idSanPham) {
        List<BinhLuan> danhSach = new ArrayList<>();
        String sql = "SELECT bl.*, nd.ho_ten FROM binh_luan bl JOIN nguoi_dung nd ON bl.id_nguoi_dung = nd.id WHERE bl.id_san_pham = ? ORDER BY bl.ngay_tao DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSanPham);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BinhLuan bl = new BinhLuan();
                bl.setId(rs.getInt("id"));
                bl.setIdNguoiDung(rs.getInt("id_nguoi_dung"));
                bl.setIdSanPham(rs.getInt("id_san_pham"));
                bl.setNoiDung(rs.getString("noi_dung"));
                bl.setNgayTao(rs.getTimestamp("ngay_tao"));
                bl.setNgayCapNhat(rs.getTimestamp("ngay_cap_nhat"));

                NguoiDung nd = new NguoiDung();
                nd.setId(rs.getInt("id_nguoi_dung"));
                nd.setHoTen(rs.getString("ho_ten"));
                bl.setNguoiDung(nd);

                danhSach.add(bl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
    
    public void themBinhLuan(BinhLuan bl) {
        String sql = "INSERT INTO binh_luan (id_nguoi_dung, id_san_pham, noi_dung, ngay_tao, ngay_cap_nhat) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bl.getIdNguoiDung());
            stmt.setInt(2, bl.getIdSanPham());
            stmt.setString(3, bl.getNoiDung());
            stmt.setTimestamp(4, bl.getNgayTao());
            stmt.setTimestamp(5, bl.getNgayCapNhat());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
