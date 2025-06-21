package BTL.daos;

import BTL.models.GioHang;
import BTL.models.GioHangChiTiet;
import BTL.models.SanPham;
import BTL.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GioHangDAO {
    private GioHangChiTietDAO gioHangChiTietDAO;

    public GioHangDAO() {
        this.gioHangChiTietDAO = new GioHangChiTietDAO();
    }

    private Connection getConnection() throws SQLException {
        return DBConnection.getInstance().getConnection();
    }

    public GioHang taoGioHang(int idNguoiDung) throws SQLException {
        String sql = "INSERT INTO gio_hang (id_nguoi_dung, trang_thai, ngay_tao) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idNguoiDung);
            stmt.setString(2, "dang_mua");
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Tạo giỏ hàng thất bại, không có bản ghi nào được thêm.");
            }

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                GioHang gioHang = new GioHang();
                gioHang.setId(rs.getInt(1));
                gioHang.setIdNguoiDung(idNguoiDung);
                gioHang.setTrangThai("dang_mua");
                gioHang.setNgayTao(new Timestamp(System.currentTimeMillis()));
                System.out.println("Tạo giỏ hàng thành công: id=" + gioHang.getId());
                return gioHang;
            } else {
                throw new SQLException("Tạo giỏ hàng thất bại, không lấy được ID.");
            }
        }
    }

    public GioHang timTheoNguoiDung(int idNguoiDung) throws SQLException {
        String sql = "SELECT * FROM gio_hang WHERE id_nguoi_dung = ? AND trang_thai = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idNguoiDung);
            stmt.setString(2, "dang_mua");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                GioHang gioHang = new GioHang();
                gioHang.setId(rs.getInt("id"));
                gioHang.setIdNguoiDung(rs.getInt("id_nguoi_dung"));
                gioHang.setTrangThai(rs.getString("trang_thai"));
                gioHang.setNgayTao(rs.getTimestamp("ngay_tao"));
                System.out.println("Tìm thấy giỏ hàng: id=" + gioHang.getId());
                return gioHang;
            }
            System.out.println("Không tìm thấy giỏ hàng cho id_nguoi_dung=" + idNguoiDung);
            return null;
        }
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
            throw new RuntimeException("Lỗi: " + e.getMessage());
        } finally {
            DBConnection.getInstance().closeConnection(conn);
        }
        return danhSach;
    }
}
