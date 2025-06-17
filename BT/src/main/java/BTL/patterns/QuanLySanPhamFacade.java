//package BTL.patterns;
//
//import java.util.List;
//import BTL.daos.SanPhamDAO;
//import BTL.models.SanPham;
//
//public class QuanLySanPhamFacade {
//    private SanPhamDAO sanPhamDAO = new SanPhamDAO();
//
//    public List<SanPham> layTatCaSanPham() {
//        return sanPhamDAO.layTatCa();
//    }
//}

package BTL.patterns;

import BTL.daos.SanPhamDAO;
import BTL.models.SanPham;

import java.util.List;

public class QuanLySanPhamFacade {
    private SanPhamDAO sanPhamDAO = new SanPhamDAO();

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
}
