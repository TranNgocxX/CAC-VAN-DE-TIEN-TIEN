package BTL.daos;

import BTL.models.GioHangChiTiet;
import BTL.models.SanPham;
import BTL.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GioHangChiTietDAO {
    private final SanPhamDAO sanPhamDAO;

    public GioHangChiTietDAO() {
        this.sanPhamDAO = new SanPhamDAO();
    }
    
    private Connection getConnection() throws SQLException {
        return DBConnection.getInstance().getConnection();
    }


    public void themHoacCapNhat(GioHangChiTiet chiTiet) throws SQLException {
        String sqlSelect = "SELECT * FROM gio_hang_chi_tiet WHERE id_gio_hang = ? AND id_san_pham = ? AND trang_thai = ?";
        String sqlInsert = "INSERT INTO gio_hang_chi_tiet (id_gio_hang, id_san_pham, so_luong, ngay_them, trang_thai) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE gio_hang_chi_tiet SET so_luong = so_luong + ?, ngay_them = ? WHERE id_gio_hang = ? AND id_san_pham = ? AND trang_thai = ?";

        try (Connection conn = getConnection()) {
            PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect);
            stmtSelect.setInt(1, chiTiet.getIdGioHang());
            stmtSelect.setInt(2, chiTiet.getSanPham().getId());
            stmtSelect.setString(3, "dang_mua");
            ResultSet rs = stmtSelect.executeQuery();

            if (rs.next()) {
                PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
                stmtUpdate.setInt(1, chiTiet.getSoLuong());
                stmtUpdate.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                stmtUpdate.setInt(3, chiTiet.getIdGioHang());
                stmtUpdate.setInt(4, chiTiet.getSanPham().getId());
                stmtUpdate.setString(5, "dang_mua");
                int rowsAffected = stmtUpdate.executeUpdate();
                System.out.println("Cập nhật chi tiết giỏ hàng: id_gio_hang=" + chiTiet.getIdGioHang() + ", id_san_pham=" + chiTiet.getSanPham().getId() + ", rows=" + rowsAffected);
            } else {
                PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert);
                stmtInsert.setInt(1, chiTiet.getIdGioHang());
                stmtInsert.setInt(2, chiTiet.getSanPham().getId());
                stmtInsert.setInt(3, chiTiet.getSoLuong());
                stmtInsert.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                stmtInsert.setString(5, "dang_mua");
                int rowsAffected = stmtInsert.executeUpdate();
                System.out.println("Thêm chi tiết giỏ hàng: id_gio_hang=" + chiTiet.getIdGioHang() + ", id_san_pham=" + chiTiet.getSanPham().getId() + ", rows=" + rowsAffected);
            }
        }
    }

    public List<GioHangChiTiet> layTheoGioHang(int idGioHang) throws SQLException {
        List<GioHangChiTiet> danhSach = new ArrayList<>();
        String sql = "SELECT ghct.*, sp.* FROM gio_hang_chi_tiet ghct JOIN san_pham sp ON ghct.id_san_pham = sp.id " +
                     "WHERE ghct.id_gio_hang = ? AND ghct.trang_thai = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idGioHang);
            stmt.setString(2, "dang_mua");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                GioHangChiTiet chiTiet = new GioHangChiTiet();
                chiTiet.setId(rs.getInt("id"));
                chiTiet.setIdGioHang(rs.getInt("id_gio_hang"));
                chiTiet.setSoLuong(rs.getInt("so_luong"));
                chiTiet.setNgayThem(rs.getTimestamp("ngay_them"));
                chiTiet.setTrangThai(rs.getString("trang_thai"));

                SanPham sp = new SanPham();
                sp.setId(rs.getInt("id_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setMoTa(rs.getString("mo_ta"));
                sp.setGia(rs.getDouble("gia"));
                sp.setSoLuong(rs.getInt("so_luong"));
                sp.setAnhUrl(rs.getString("anh_url"));
                sp.setIdLoai(rs.getInt("id_loai"));
                chiTiet.setSanPham(sp);

                danhSach.add(chiTiet);
            }
            System.out.println("Lấy danh sách giỏ hàng: id_gio_hang=" + idGioHang + ", số lượng=" + danhSach.size());
            return danhSach;
        }
    }


//cho chức năng quản lý giỏ hàng (admin) 
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
    
 //cho chức năng quản lý giỏ hàng (admin) 
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

            conn.commit(); 
            } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); 
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi cập nhật chi tiết giỏ hàng: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.getInstance().closeConnection(conn);
        }
    }
    
 //cho chức năng quản lý giỏ hàng (admin) 
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
