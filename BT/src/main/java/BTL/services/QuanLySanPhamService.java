package BTL.services;

import BTL.daos.SanPhamDAO;
import BTL.patterns.DAOFactory;
import BTL.models.SanPham;

import java.util.List;

public class QuanLySanPhamService {
    private SanPhamDAO sanPhamDAO;

    public QuanLySanPhamService() {
        this.sanPhamDAO = DAOFactory.createSanPhamDAO();
    }

    public List<SanPham> layTatCaSanPham() {
        return sanPhamDAO.layTatCa();
    }

    public SanPham timTheoId(int id) {
        return sanPhamDAO.timTheoId(id);
    }

    public void luuSanPham(SanPham sp) {
        sanPhamDAO.luuSanPham(sp);
    }

    public void xoaSanPham(int id) {
        sanPhamDAO.xoaSanPham(id);
    }

    public List<SanPham> layTheoLoai(int idLoai) {
        return sanPhamDAO.layTheoLoai(idLoai);
    }
}
