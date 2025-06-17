package BTL.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import BTL.models.BinhLuan;
import BTL.utils.DBConnection;

public class BinhLuanDAO {
    private static BinhLuanDAO instance;
    private Connection connection;
    
    private BinhLuanDAO() {
        connection = DBConnection.getInstance().getConnection();
    }
    
    public static synchronized BinhLuanDAO getInstance() {
        if (instance == null) {
            instance = new BinhLuanDAO();
        }
        return instance;
    }
    
    public boolean themBinhLuan(BinhLuan binhLuan) {
        String sql = "INSERT INTO binh_luan (id_nguoi_dung, id_san_pham, noi_dung) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, binhLuan.getIdNguoiDung());
            stmt.setInt(2, binhLuan.getIdSanPham());
            stmt.setString(3, binhLuan.getNoiDung());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<BinhLuan> layBinhLuanTheoSanPham(int idSanPham) {
        List<BinhLuan> dsBinhLuan = new ArrayList<>();
        String sql = "SELECT * FROM binh_luan WHERE id_san_pham = ? ORDER BY ngay_tao DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSanPham);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsBinhLuan.add(taoBinhLuanTuResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsBinhLuan;
    }
    public List<BinhLuan> layBinhLuanTheoNguoiDung(int idNguoiDung) {
        List<BinhLuan> dsBinhLuan = new ArrayList<>();
        String sql = "SELECT * FROM binh_luan WHERE id_nguoi_dung = ? ORDER BY ngay_tao DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idNguoiDung);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsBinhLuan.add(taoBinhLuanTuResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsBinhLuan;
    }

    private BinhLuan taoBinhLuanTuResultSet(ResultSet rs) throws SQLException {
        BinhLuan binhLuan = new BinhLuan();
        binhLuan.setId(rs.getInt("id"));
        binhLuan.setIdNguoiDung(rs.getInt("id_nguoi_dung"));
        binhLuan.setIdSanPham(rs.getInt("id_san_pham"));
        binhLuan.setNoiDung(rs.getString("noi_dung"));
        binhLuan.setNgayTao(rs.getTimestamp("ngay_tao"));
        binhLuan.setNgayCapNhat(rs.getTimestamp("ngay_cap_nhat"));
        return binhLuan;
    }
}