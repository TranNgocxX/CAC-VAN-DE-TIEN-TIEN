package BTL.daos;

import BTL.models.GioHangChiTiet;
import BTL.models.SanPham;
import BTL.utils.DBConnection;

import java.sql.*;
import java.util.*;

public class GioHangDAO {
    private Connection connection = DBConnection.getInstance().getConnection();

    public void themVaoGio(int idNguoiDung, int idSanPham, int soLuong) {
        try (Connection conn = DBConnection.getInstance().getConnection()) {

            String sqlTimGH = "SELECT id FROM gio_hang WHERE id_nguoi_dung = ? AND trang_thai = 'dang_mua'";
            PreparedStatement psTim = conn.prepareStatement(sqlTimGH);
            psTim.setInt(1, idNguoiDung);
            ResultSet rsGH = psTim.executeQuery();

            int idGioHang = -1;
            if (rsGH.next()) {
                idGioHang = rsGH.getInt("id");
            } else {
                String sqlTao = "INSERT INTO gio_hang(id_nguoi_dung, trang_thai, ngay_tao) VALUES (?, 'dang_mua', NOW())";
                PreparedStatement psTao = conn.prepareStatement(sqlTao, Statement.RETURN_GENERATED_KEYS);
                psTao.setInt(1, idNguoiDung);
                psTao.executeUpdate();
                ResultSet rsNew = psTao.getGeneratedKeys();
                if (rsNew.next()) idGioHang = rsNew.getInt(1);
            }

            // B2: Kiểm tra sp đã có trong giỏ chưa
            String sqlCheck = "SELECT id, so_luong FROM gio_hang_chi_tiet WHERE id_gio_hang = ? AND id_san_pham = ?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, idGioHang);
            psCheck.setInt(2, idSanPham);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                int idCT = rs.getInt("id");
                int soLuongHienTai = rs.getInt("so_luong");
                String sqlUpdate = "UPDATE gio_hang_chi_tiet SET so_luong = ?, ngay_them = NOW() WHERE id = ?";
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, soLuongHienTai + soLuong);
                psUpdate.setInt(2, idCT);
                psUpdate.executeUpdate();
            } else {
                String sqlInsert = "INSERT INTO gio_hang_chi_tiet(id_gio_hang, id_san_pham, so_luong, ngay_them) VALUES (?, ?, ?, NOW())";
                PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
                psInsert.setInt(1, idGioHang);
                psInsert.setInt(2, idSanPham);
                psInsert.setInt(3, soLuong);
                psInsert.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<GioHangChiTiet> layTheoNguoiDung(int idNguoiDung) {
        List<GioHangChiTiet> danhSach = new ArrayList<>();

        String sql = "SELECT ghct.*, sp.ten_san_pham, sp.gia, sp.anh_url " +
                     "FROM gio_hang_chi_tiet ghct " +
                     "JOIN gio_hang gh ON gh.id = ghct.id_gio_hang " +
                     "JOIN san_pham sp ON sp.id = ghct.id_san_pham " +
                     "WHERE gh.id_nguoi_dung = ? AND gh.trang_thai = 'dang_mua'";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idNguoiDung);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                GioHangChiTiet ct = new GioHangChiTiet();
                ct.setId(rs.getInt("id"));
                ct.setIdGioHang(rs.getInt("id_gio_hang"));
                ct.setSoLuong(rs.getInt("so_luong"));
                ct.setNgayThem(rs.getTimestamp("ngay_them"));

                SanPham sp = new SanPham();
                sp.setId(rs.getInt("id_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setGia(rs.getDouble("gia"));
                sp.setAnhUrl(rs.getString("anh_url"));

                ct.setSanPham(sp);
                danhSach.add(ct);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSach;
    }
}
