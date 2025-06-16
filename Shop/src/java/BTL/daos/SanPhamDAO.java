package BTL.daos;

import BTL.models.LoaiSanPham;
import BTL.models.SanPham;
import BTL.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    private static SanPhamDAO instance;
    private Connection connection;

    private SanPhamDAO() {
        connection = DBConnection.getInstance().getConnection();
    }

    public static synchronized SanPhamDAO getInstance() {
        if (instance == null) {
            instance = new SanPhamDAO();
        }
        return instance;
    }

    public List<SanPham> layTatCa() {
        List<SanPham> dsSanPham = new ArrayList<>();
        String sql = "SELECT * FROM san_pham";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsSanPham.add(taoSanPhamTuResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSanPham;
    }

    public SanPham timTheoId(int id) {
        String sql = "SELECT * FROM san_pham WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return taoSanPhamTuResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean themSanPham(SanPham sanPham) {
        String sql = "INSERT INTO san_pham (ten_san_pham, mo_ta, gia, so_luong, anh_url, id_loai, ngay_tao, ngay_cap_nhat) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sanPham.getTenSanPham());
            stmt.setString(2, sanPham.getMoTa());
            stmt.setDouble(3, sanPham.getGia());
            stmt.setInt(4, sanPham.getSoLuong());
            stmt.setString(5, sanPham.getAnhUrl());
            stmt.setInt(6, sanPham.getIdLoai());
            stmt.setTimestamp(7, sanPham.getNgayTao());
            stmt.setTimestamp(8, sanPham.getNgayCapNhat());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaSanPham(SanPham sanPham) {
        String sql = "UPDATE san_pham SET ten_san_pham = ?, mo_ta = ?, gia = ?, so_luong = ?, anh_url = ?, id_loai = ?, ngay_cap_nhat = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sanPham.getTenSanPham());
            stmt.setString(2, sanPham.getMoTa());
            stmt.setDouble(3, sanPham.getGia());
            stmt.setInt(4, sanPham.getSoLuong());
            stmt.setString(5, sanPham.getAnhUrl());
            stmt.setInt(6, sanPham.getIdLoai());
            stmt.setTimestamp(7, sanPham.getNgayCapNhat());
            stmt.setInt(8, sanPham.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaSanPham(int id) {
        String sql = "DELETE FROM san_pham WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LoaiSanPham> layTatCaLoaiSanPham() {
        List<LoaiSanPham> dsLoaiSanPham = new ArrayList<>();
        String sql = "SELECT * FROM loai_san_pham";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LoaiSanPham loai = new LoaiSanPham();
                loai.setId(rs.getInt("id"));
                loai.setTenLoai(rs.getString("ten_loai"));
                dsLoaiSanPham.add(loai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoaiSanPham;
    }

    private SanPham taoSanPhamTuResultSet(ResultSet rs) throws SQLException {
        return new SanPham.SanPhamBuilder()
                .setId(rs.getInt("id"))
                .setTenSanPham(rs.getString("ten_san_pham"))
                .setMoTa(rs.getString("mo_ta"))
                .setGia(rs.getDouble("gia"))
                .setSoLuong(rs.getInt("so_luong"))
                .setAnhUrl(rs.getString("anh_url"))
                .setIdLoai(rs.getInt("id_loai"))
                .setNgayTao(rs.getTimestamp("ngay_tao"))
                .setNgayCapNhat(rs.getTimestamp("ngay_cap_nhat"))
                .build();
    }
}