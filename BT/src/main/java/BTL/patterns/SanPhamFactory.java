package BTL.patterns;

import BTL.models.SanPham;

import java.sql.Timestamp;

public class SanPhamFactory {
    public static SanPham taoSanPhamMoi(String ten, String moTa, double gia, int soLuong,
                                        String anhUrl, int idLoai) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SanPham sp = new SanPham();
        sp.setTenSanPham(ten);
        sp.setMoTa(moTa);
        sp.setGia(gia);
        sp.setSoLuong(soLuong);
        sp.setAnhUrl(anhUrl);
        sp.setIdLoai(idLoai);
        sp.setNgayTao(now);
        sp.setNgayCapNhat(now);
        return sp;
    }
}
