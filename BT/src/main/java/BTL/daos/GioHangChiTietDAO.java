package BTL.daos;

import BTL.models.GioHangChiTiet;
import BTL.models.SanPham;
import BTL.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GioHangChiTietDAO {
    private final SanPhamDAO sanPhamDAO;

    public GioHangChiTietDAO() {
        this.sanPhamDAO = new SanPhamDAO();
    }

    public void themHoacCapNhatSanPham(int idGioHang, int idSanPham, int soLuong) {
        String checkSql = "SELECT * FROM gio_hang_chi_tiet WHERE id_gio_hang = ? AND id_san_pham = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            SanPham sanPham = sanPhamDAO.laySanPhamTheoId(idSanPham);
            if (sanPham == null) {
                throw new SQLException("Sản phẩm không tồn tại.");
            }

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, idGioHang);
                checkStmt.setInt(2, idSanPham);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        int soLuongHienTai = rs.getInt("so_luong");
                        String updateSql = "UPDATE gio_hang_chi_tiet SET so_luong = ?, ngay_them = ? WHERE id = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, soLuongHienTai + soLuong);
                            updateStmt.setTimestamp(2, new Timestamp(new Date().getTime()));
                            updateStmt.setInt(3, rs.getInt("id"));
                            updateStmt.executeUpdate();
                        }
                    } else {
                        String insertSql = "INSERT INTO gio_hang_chi_tiet (id_gio_hang, id_san_pham, so_luong, ngay_them, trang_thai) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setInt(1, idGioHang);
                            insertStmt.setInt(2, idSanPham);
                            insertStmt.setInt(3, soLuong);
                            insertStmt.setTimestamp(4, new Timestamp(new Date().getTime()));
                            insertStmt.setString(5, "dang_mua"); // Thêm trạng thái mặc định
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm/cập nhật sản phẩm vào giỏ hàng: " + e.getMessage());
        } finally {
            DBConnection.getInstance().closeConnection(conn);
        }
    }

    public List<GioHangChiTiet> layDanhSachSanPhamTheoGioHang(int idGioHang) {
        List<GioHangChiTiet> danhSach = new ArrayList<>();
        String sql = "SELECT ghct.*, sp.ten_san_pham, sp.gia, sp.anh_url " +
                     "FROM gio_hang_chi_tiet ghct " +
                     "JOIN san_pham sp ON sp.id = ghct.id_san_pham " +
                     "WHERE ghct.id_gio_hang = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idGioHang);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        GioHangChiTiet ct = new GioHangChiTiet();
                        ct.setId(rs.getInt("id"));
                        ct.setIdGioHang(rs.getInt("id_gio_hang"));
                        ct.setSoLuong(rs.getInt("so_luong"));
                        ct.setNgayThem(rs.getTimestamp("ngay_them"));
                        ct.setTrangThai(rs.getString("trang_thai")); // Lấy trạng thái

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
            throw new RuntimeException("Lỗi khi lấy danh sách chi tiết giỏ hàng: " + e.getMessage());
        } finally {
            DBConnection.getInstance().closeConnection(conn);
        }
        return danhSach;
    }

    public void capNhatChiTietGioHang(int idChiTiet, int soLuong, String trangThai) {
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            // Cập nhật số lượng và trạng thái (nếu có) trong gio_hang_chi_tiet
            String sqlChiTiet;
            PreparedStatement stmtChiTiet;
            if (trangThai != null) {
                sqlChiTiet = "UPDATE gio_hang_chi_tiet SET so_luong = ?, trang_thai = ? WHERE id = ?";
                stmtChiTiet = conn.prepareStatement(sqlChiTiet);
                stmtChiTiet.setInt(1, soLuong);
                stmtChiTiet.setString(2, trangThai);
                stmtChiTiet.setInt(3, idChiTiet);
            } else {
                sqlChiTiet = "UPDATE gio_hang_chi_tiet SET so_luong = ? WHERE id = ?";
                stmtChiTiet = conn.prepareStatement(sqlChiTiet);
                stmtChiTiet.setInt(1, soLuong);
                stmtChiTiet.setInt(2, idChiTiet);
            }

            int rowsAffected = stmtChiTiet.executeUpdate();
            stmtChiTiet.close();

            if (rowsAffected == 0) {
                throw new SQLException("Không tìm thấy chi tiết giỏ hàng với ID: " + idChiTiet);
            }

            conn.commit(); // Xác nhận giao dịch
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Hoàn tác nếu có lỗi
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi cập nhật chi tiết giỏ hàng: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true); // Khôi phục chế độ auto-commit
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.getInstance().closeConnection(conn);
        }
    }

    public void xoaChiTiet(int idChiTiet) {
        String sql = "DELETE FROM gio_hang_chi_tiet WHERE id = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idChiTiet);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xóa chi tiết giỏ hàng: " + e.getMessage());
        } finally {
            DBConnection.getInstance().closeConnection(conn);
        }
    }
}
