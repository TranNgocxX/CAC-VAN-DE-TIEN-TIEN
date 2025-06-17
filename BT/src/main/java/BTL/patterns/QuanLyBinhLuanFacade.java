package BTL.patterns;

import java.util.List;
import BTL.daos.BinhLuanDAO;
import BTL.models.BinhLuan;

public class QuanLyBinhLuanFacade {
    private BinhLuanDAO binhLuanDAO;
    private BinhLuanOriginator originator;

    public QuanLyBinhLuanFacade() {
        this.binhLuanDAO = BinhLuanDAO.getInstance();
        this.originator = new BinhLuanOriginator();
    }

    public boolean themBinhLuan(BinhLuan binhLuan) {
        // Lưu trạng thái trước khi thêm
        originator.setBinhLuan(binhLuan);
        BinhLuanMemento memento = originator.luuTrangThai();

        boolean result = binhLuanDAO.themBinhLuan(binhLuan);

        if (!result) {
            // Phục hồi trạng thái nếu thất bại
            originator.phucHoiTrangThai(memento);
        }

        return result;
    }

    public List<BinhLuan> layBinhLuanTheoSanPham(int idSanPham) {
        return binhLuanDAO.layBinhLuanTheoSanPham(idSanPham);
    }

    public List<BinhLuan> layBinhLuanTheoNguoiDung(int idNguoiDung) {
        return binhLuanDAO.layBinhLuanTheoNguoiDung(idNguoiDung);
    }
}

