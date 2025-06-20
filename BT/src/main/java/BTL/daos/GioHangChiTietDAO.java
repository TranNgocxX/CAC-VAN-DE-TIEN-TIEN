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
            double donGia = sanPham.getGia();

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, idGioHang);
                checkStmt.setInt(2, idSanPham);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        int soLuongHienTai = rs.getInt("so_luong");
                        //String updateSql = "UPDATE gio_hang_chi_tiet SET so_luong = ?, ngay_them = ?, tong_tien = ? WHERE id = ?";
                        String updateSql = "UPDATE gio_hang_chi_tiet SET so_luong = ?, ngay_them = ? WHERE id = ?";

                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, soLuongHienTai + soLuong);
                            updateStmt.setTimestamp(2, new Timestamp(new Date().getTime()));
                            //updateStmt.setDouble(3, (soLuongHienTai + soLuong) * donGia);
                            updateStmt.setInt(3, rs.getInt("id"));
                            updateStmt.executeUpdate();
                        }
                    } else {
                        //String insertSql = "INSERT INTO gio_hang_chi_tiet (id_gio_hang, id_san_pham, so_luong, ngay_them, don_gia, tong_tien, trang_thai) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        String insertSql = "INSERT INTO gio_hang_chi_tiet (id_gio_hang, id_san_pham, so_luong, ngay_them, trang_thai) VALUES (?, ?, ?, ?, ?,)";

                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setInt(1, idGioHang);
                            insertStmt.setInt(2, idSanPham);
                            insertStmt.setInt(3, soLuong);
                            insertStmt.setTimestamp(4, new Timestamp(new Date().getTime()));
                            //insertStmt.setDouble(5, donGia);
                            //insertStmt.setDouble(6, soLuong * donGia);
                            insertStmt.setString(5, "dang_mua");
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

    public void capNhatSoLuong(int idChiTiet, int soLuongMoi) {
        String sql = "UPDATE gio_hang_chi_tiet SET so_luong = ?, tong_tien = so_luong * don_gia WHERE id = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, soLuongMoi);
                stmt.setInt(2, idChiTiet);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi cập nhật số lượng: " + e.getMessage());
        } finally {
            DBConnection.getInstance().closeConnection(conn);
        }
    }

    public void capNhatTrangThai(int idChiTiet, String trangThaiMoi) {
        String sql = "UPDATE gio_hang_chi_tiet SET trang_thai = ?, tong_tien = so_luong * don_gia WHERE id = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, trangThaiMoi);
                stmt.setInt(2, idChiTiet);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi cập nhật trạng thái: " + e.getMessage());
        } finally {
            DBConnection.getInstance().closeConnection(conn);
        }
    }

    public void capNhatTrangThaiVaSoLuong(int idChiTiet, String trangThaiMoi, int soLuongMoi) {
        String sql = "UPDATE gio_hang_chi_tiet SET trang_thai = ?, so_luong = ?, tong_tien = so_luong * don_gia WHERE id = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, trangThaiMoi);
                stmt.setInt(2, soLuongMoi);
                stmt.setInt(3, idChiTiet);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi cập nhật trạng thái và số lượng: " + e.getMessage());
        } finally {
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
