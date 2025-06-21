// ==== BinhLuanDAO.java ====
package BTL.daos;

import BTL.models.BinhLuan;
import BTL.models.NguoiDung;
import BTL.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BinhLuanDAO {
    public List<BinhLuan> layTheoSanPham(int idSanPham) {
        List<BinhLuan> danhSach = new ArrayList<>();
        String sql = """
            SELECT bl.*, nd.ho_ten 
            FROM binh_luan bl 
            JOIN nguoi_dung nd ON bl.id_nguoi_dung = nd.id 
            WHERE bl.id_san_pham = ? 
            ORDER BY bl.ngay_tao DESC
        """;

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSanPham);
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public void themBinhLuan(BinhLuan bl) {
        String sql = """
            INSERT INTO binh_luan 
            (id_nguoi_dung, id_san_pham, noi_dung, ngay_tao, ngay_cap_nhat) 
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
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

    public void suaBinhLuan(BinhLuan bl) {
        String sql = """
            UPDATE binh_luan 
            SET noi_dung = ?, ngay_cap_nhat = ? 
            WHERE id = ? AND id_nguoi_dung = ?
        """;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, bl.getNoiDung());
            stmt.setTimestamp(2, bl.getNgayCapNhat());
            stmt.setInt(3, bl.getId());
            stmt.setInt(4, bl.getIdNguoiDung());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void xoaBinhLuan(int idBinhLuan, int idNguoiDung) {
        String sql = "DELETE FROM binh_luan WHERE id = ? AND id_nguoi_dung = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idBinhLuan);
            stmt.setInt(2, idNguoiDung);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
