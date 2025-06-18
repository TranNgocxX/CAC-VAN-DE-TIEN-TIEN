package BTL.patterns;

import BTL.daos.BinhLuanDAO;
import BTL.models.BinhLuan;
import java.util.List;

public class QuanLyBinhLuanFacade {
    private BinhLuanDAO binhLuanDAO = new BinhLuanDAO();

    public List<BinhLuan> layTheoSanPham(int idSanPham) {
        return binhLuanDAO.layTheoSanPham(idSanPham);
    }

    public void themBinhLuan(BinhLuan bl) {
        binhLuanDAO.themBinhLuan(bl);
    }
}
