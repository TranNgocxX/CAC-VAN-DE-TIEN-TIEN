package BTL.daos;

import BTL.models.GioHangChiTiet;
import BTL.models.SanPham;
import BTL.utils.DBConnection;

import java.sql.*;
import java.util.*;

public class ChiTietGioHangDAO {
    private SanPhamDAO sanPhamDAO = new SanPhamDAO();

    public void capNhatSoLuong(int idChiTiet, int soLuongMoi) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "UPDATE gio_hang_chi_tiet SET so_luong = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, soLuongMoi);
            stmt.setInt(2, idChiTiet);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void capNhatTrangThai(int idChiTiet, String trangThaiMoi) {
        String sql = "UPDATE gio_hang_chi_tiet SET trang_thai = ?, tong_tien = so_luong * don_gia WHERE id = ?";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, trangThaiMoi);
                stmt.setInt(2, idChiTiet);
                stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void capNhatTrangThaiVaSoLuong(int idChiTiet, String trangThaiMoi, int soLuongMoi) {
    	String sql = "UPDATE gio_hang_chi_tiet SET trang_thai = ?, so_luong = ? WHERE id = ?";        
    	try {
            Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, trangThaiMoi);
            stmt.setInt(2, soLuongMoi);
            stmt.setInt(3, idChiTiet);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // hoặc log lỗi
        }
    }


    public void xoaChiTiet(int idChiTiet) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM gio_hang_chi_tiet WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idChiTiet);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}