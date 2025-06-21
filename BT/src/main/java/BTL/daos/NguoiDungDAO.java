package BTL.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import BTL.models.NguoiDung;
import BTL.utils.DBConnection;

public class NguoiDungDAO {
    private static NguoiDungDAO instance;

    private NguoiDungDAO() {}

    public static synchronized NguoiDungDAO getInstance() {
        if (instance == null) {
            instance = new NguoiDungDAO();
        }
        return instance;
    }

    public boolean themNguoiDung(NguoiDung nguoiDung) {
        String sql = "INSERT INTO nguoi_dung (ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nguoiDung.getTenDangNhap());
            stmt.setString(2, nguoiDung.getMatKhau());
            stmt.setString(3, nguoiDung.getHoTen());
            stmt.setString(4, nguoiDung.getEmail());
            stmt.setString(5, nguoiDung.getSoDienThoai());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public NguoiDung timTheoTenDangNhap(String tenDangNhap) {
        String sql = "SELECT * FROM nguoi_dung WHERE ten_dang_nhap = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tenDangNhap);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return taoNguoiDungTuResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public NguoiDung timTheoId(int id) {
        String sql = "SELECT * FROM nguoi_dung WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return taoNguoiDungTuResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NguoiDung> layTatCa() {
        List<NguoiDung> dsNguoiDung = new ArrayList<>();
        String sql = "SELECT * FROM nguoi_dung";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dsNguoiDung.add(taoNguoiDungTuResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNguoiDung;
    }

    private NguoiDung taoNguoiDungTuResultSet(ResultSet rs) throws SQLException {
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setId(rs.getInt("id"));
        nguoiDung.setTenDangNhap(rs.getString("ten_dang_nhap"));
        nguoiDung.setMatKhau(rs.getString("mat_khau"));
        nguoiDung.setHoTen(rs.getString("ho_ten"));
        nguoiDung.setEmail(rs.getString("email"));
        nguoiDung.setSoDienThoai(rs.getString("so_dien_thoai"));
        nguoiDung.setVaiTro(rs.getString("vai_tro"));
        nguoiDung.setNgayTao(rs.getTimestamp("ngay_tao"));
        return nguoiDung;
    }
}
