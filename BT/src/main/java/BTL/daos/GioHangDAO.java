package BTL.daos;

import BTL.models.GioHang;
import BTL.models.GioHangChiTiet;
import BTL.models.SanPham;
import BTL.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GioHangDAO {
    private GioHangChiTietDAO gioHangChiTietDAO;

    public GioHangDAO() {
        this.gioHangChiTietDAO = new GioHangChiTietDAO();
    }

    public GioHang layHoacTaoGioHang(int idNguoiDung) {
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM gio_hang WHERE id_nguoi_dung = ? AND trang_thai = 'dang_mua'";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idNguoiDung);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        GioHang gioHang = new GioHang(
                            id,
                            rs.getInt("id_nguoi_dung"),
                            rs.getString("trang_thai"),
                            rs.getTimestamp("ngay_tao")
                        );
                        gioHang.setDanhSachSanPham(gioHangChiTietDAO.layDanhSachSanPhamTheoGioHang(id));
                        return gioHang;
                    }
                }
            }

            String insertSql = "INSERT INTO gio_hang (id_nguoi_dung, trang_thai, ngay_tao) VALUES (?, 'dang_mua', ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                insertStmt.setInt(1, idNguoiDung);
                insertStmt.setTimestamp(2, new Timestamp(new Date().getTime()));
                insertStmt.executeUpdate();

                try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        return new GioHang(id, idNguoiDung, "dang_mua", new Date());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy/tạo giỏ hàng: " + e.getMessage());
        } finally {
            DBConnection.getInstance().closeConnection(conn);
        }
        return null;
    }

    public List<GioHangChiTiet> layTheoNguoiDung(int idNguoiDung) {
        List<GioHangChiTiet> danhSach = new ArrayList<>();
        String sql = "SELECT ghct.*, sp.ten_san_pham, sp.gia, sp.anh_url " +
                     "FROM gio_hang_chi_tiet ghct " +
                     "JOIN gio_hang gh ON gh.id = ghct.id_gio_hang " +
                     "JOIN san_pham sp ON sp.id = ghct.id_san_pham " +
                     "WHERE gh.id_nguoi_dung = ? AND gh.trang_thai = 'dang_mua'";
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idNguoiDung);
                try (ResultSet rs = stmt.executeQuery()) {
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy chi tiết giỏ hàng theo người dùng: " + e.getMessage());
        } finally {
            DBConnection.getInstance().closeConnection(conn);
        }
        return danhSach;
    }
}
