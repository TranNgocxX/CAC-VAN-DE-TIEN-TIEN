package BTL.daos;

import BTL.models.ThongTinGioHangTT;
import BTL.utils.DBConnection;

import java.sql.*;
import java.util.*;

public class ThongTinGioHangDAO {
    public List<ThongTinGioHangTT> layThongTinChiTietGioHang() {
        List<ThongTinGioHangTT> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = """
                SELECT 
				    ct.id AS idChiTiet,
				    nd.ho_ten AS tenNguoiDung,
				    sp.ten_san_pham AS tenSanPham,
				    lsp.ten_loai AS tenLoai,
				    ct.so_luong,
				    sp.gia,
				    (ct.so_luong * sp.gia) AS tongTien,
				    ct.ngay_them,
				    gh.trang_thai AS trangThai
				FROM gio_hang_chi_tiet ct
				JOIN gio_hang gh ON ct.id_gio_hang = gh.id
				JOIN nguoi_dung nd ON gh.id_nguoi_dung = nd.id
				JOIN san_pham sp ON ct.id_san_pham = sp.id
				JOIN loai_san_pham lsp ON sp.id_loai = lsp.id

            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	ThongTinGioHangTT dto = new ThongTinGioHangTT();
            	dto.setIdChiTiet(rs.getInt("idChiTiet"));
            	dto.setTenNguoiDung(rs.getString("tenNguoiDung"));
            	dto.setTenSanPham(rs.getString("tenSanPham"));
            	dto.setTenLoai(rs.getString("tenLoai"));
            	dto.setSoLuong(rs.getInt("so_luong"));
            	dto.setGia(rs.getDouble("gia"));
            	dto.setTongTien(rs.getDouble("tongTien"));
            	dto.setNgayThem(rs.getTimestamp("ngay_them"));
            	dto.setTrangThai(rs.getString("trangThai"));

                list.add(dto);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}