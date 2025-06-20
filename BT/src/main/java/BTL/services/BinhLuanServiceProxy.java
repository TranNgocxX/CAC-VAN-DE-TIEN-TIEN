package BTL.services;

import BTL.daos.BinhLuanDAO;
import BTL.models.BinhLuan;
import java.util.List;

public class BinhLuanServiceProxy implements IBinhLuanService {
    private final BinhLuanDAO binhLuanDAO = new BinhLuanDAO();

    @Override
    public List<BinhLuan> layTheoSanPham(int idSanPham) {
        return binhLuanDAO.layTheoSanPham(idSanPham);
    }

    @Override
    public void themBinhLuan(BinhLuan bl) {
        binhLuanDAO.themBinhLuan(bl);
    }

    @Override
    public void suaBinhLuan(BinhLuan bl) {
        binhLuanDAO.suaBinhLuan(bl);
    }
    
    @Override
    public void xoaBinhLuan(int idBinhLuan, int idNguoiDung) {
        binhLuanDAO.xoaBinhLuan(idBinhLuan, idNguoiDung);
    }
}