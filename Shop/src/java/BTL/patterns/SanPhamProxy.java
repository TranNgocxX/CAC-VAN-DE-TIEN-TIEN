package BTL.patterns;

import BTL.daos.SanPhamDAO;
import BTL.models.LoaiSanPham;
import BTL.models.NguoiDung;
import BTL.models.SanPham;

import java.util.List;

public class SanPhamProxy {
    private SanPhamDAO sanPhamDAO;
    private NguoiDung nguoiDung;

    public SanPhamProxy(NguoiDung nguoiDung) {
        this.sanPhamDAO = SanPhamDAO.getInstance();
        this.nguoiDung = nguoiDung;
    }

    private void kiemTraQuyenAdmin() throws SecurityException {
        if (nguoiDung == null || !"admin".equals(nguoiDung.getVaiTro())) {
            throw new SecurityException("Bạn không có quyền truy cập chức năng này.");
        }
    }

    public List<SanPham> layTatCa() throws SecurityException {
        kiemTraQuyenAdmin();
        return sanPhamDAO.layTatCa();
    }

    public SanPham timTheoId(int id) throws SecurityException {
        kiemTraQuyenAdmin();
        return sanPhamDAO.timTheoId(id);
    }

    public boolean themSanPham(SanPham sanPham) throws SecurityException {
        kiemTraQuyenAdmin();
        return sanPhamDAO.themSanPham(sanPham);
    }

    public boolean suaSanPham(SanPham sanPham) throws SecurityException {
        kiemTraQuyenAdmin();
        return sanPhamDAO.suaSanPham(sanPham);
    }

    public boolean xoaSanPham(int id) throws SecurityException {
        kiemTraQuyenAdmin();
        return sanPhamDAO.xoaSanPham(id);
    }

    public List<LoaiSanPham> layTatCaLoaiSanPham() {
        return sanPhamDAO.layTatCaLoaiSanPham();
    }
}