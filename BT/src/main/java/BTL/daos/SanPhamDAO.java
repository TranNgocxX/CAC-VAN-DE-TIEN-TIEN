package BTL.daos;

import java.sql.*;
import java.util.*;
import BTL.models.SanPham;
import BTL.utils.DBConnection;

public class SanPhamDAO {
    private Connection connection = DBConnection.getInstance().getConnection();

    public List<SanPham> layTatCa() {
        List<SanPham> ds = new ArrayList<>();
        String sql = "SELECT * FROM san_pham";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SanPham sp = taoSanPhamTuResultSet(rs);
                ds.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
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

    public void luuSanPham(SanPham sp) {
        if (sp.getId() == 0) {
            String sql = "INSERT INTO san_pham (ten_san_pham, mo_ta, gia, so_luong, anh_url, id_loai, ngay_tao, ngay_cap_nhat) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                setParamsForSave(stmt, sp);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String sql = "UPDATE san_pham SET ten_san_pham = ?, mo_ta = ?, gia = ?, so_luong = ?, anh_url = ?, id_loai = ?, ngay_tao = ?, ngay_cap_nhat = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                setParamsForSave(stmt, sp);
                stmt.setInt(9, sp.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void xoaSanPham(int id) {
        String sql = "DELETE FROM san_pham WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setParamsForSave(PreparedStatement stmt, SanPham sp) throws SQLException {
        stmt.setString(1, sp.getTenSanPham());
        stmt.setString(2, sp.getMoTa());
        stmt.setDouble(3, sp.getGia());
        stmt.setInt(4, sp.getSoLuong());
        stmt.setString(5, sp.getAnhUrl());
        stmt.setInt(6, sp.getIdLoai());
        stmt.setTimestamp(7, sp.getNgayTao());
        stmt.setTimestamp(8, sp.getNgayCapNhat());
    }

    private SanPham taoSanPhamTuResultSet(ResultSet rs) throws SQLException {
        SanPham sp = new SanPham();
        sp.setId(rs.getInt("id"));
        sp.setTenSanPham(rs.getString("ten_san_pham"));
        sp.setMoTa(rs.getString("mo_ta"));
        sp.setGia(rs.getDouble("gia"));
        sp.setSoLuong(rs.getInt("so_luong"));
        sp.setAnhUrl(rs.getString("anh_url"));
        sp.setIdLoai(rs.getInt("id_loai"));
        sp.setNgayTao(rs.getTimestamp("ngay_tao"));
        sp.setNgayCapNhat(rs.getTimestamp("ngay_cap_nhat"));
        return sp;
    }
    
    // hiển thị trên trang chủ
    public List<SanPham> layTheoLoai(int idLoai) {
        List<SanPham> ds = new ArrayList<>();
        String sql = "SELECT * FROM san_pham WHERE id_loai = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idLoai);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPham sp = taoSanPhamTuResultSet(rs);
                ds.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

}

//package BTL.daos;
//
//import java.sql.*;
//import java.util.*;
//import BTL.models.SanPham;
//import BTL.utils.DBConnection;
//
//public class SanPhamDAO {
//    private Connection connection = DBConnection.getInstance().getConnection();
//
//    public List<SanPham> layTatCa() {
//        List<SanPham> ds = new ArrayList<>();
//        String sql = "SELECT * FROM san_pham";
//        try (PreparedStatement stmt = connection.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                SanPham sp = new SanPham();
//                sp.setId(rs.getInt("id"));
//                sp.setTenSanPham(rs.getString("ten_san_pham"));
//                sp.setMoTa(rs.getString("mo_ta"));
//                sp.setGia(rs.getDouble("gia"));
//                sp.setSoLuong(rs.getInt("so_luong"));
//                sp.setAnhUrl(rs.getString("anh_url"));
//                sp.setIdLoai(rs.getInt("id_loai"));
//                sp.setNgayTao(rs.getTimestamp("ngay_tao"));
//                sp.setNgayCapNhat(rs.getTimestamp("ngay_cap_nhat"));
//                ds.add(sp);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return ds;
//    }
//}