package BTL.patterns;

import BTL.models.SanPham;
import BTL.services.QuanLySanPhamService;

import java.util.List;

public class SanPhamFacade {
    private QuanLySanPhamService service;

    public SanPhamFacade() {
        this.service = new QuanLySanPhamService();
    }

    public void themSanPham(String ten, String moTa, double gia, int soLuong,
                            String anhUrl, int idLoai) {
        SanPham sp = SanPhamFactory.taoSanPhamMoi(ten, moTa, gia, soLuong, anhUrl, idLoai);
        service.luuSanPham(sp);
    }

    public List<SanPham> layTatCa() {
        return service.layTatCaSanPham();
    }

    public SanPham timTheoId(int id) {
        return service.timTheoId(id);
    }

    public void xoa(int id) {
        service.xoaSanPham(id);
    }

    public List<SanPham> layTheoLoai(int idLoai) {
        return service.layTheoLoai(idLoai);
    }
}
