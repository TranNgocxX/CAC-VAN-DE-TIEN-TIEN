package BTL.services;

import BTL.models.BinhLuan;
import java.util.List;

public interface IBinhLuanService {
    List<BinhLuan> layTheoSanPham(int idSanPham);
    void themBinhLuan(BinhLuan bl);
    void suaBinhLuan(BinhLuan bl);
    void xoaBinhLuan(int idBinhLuan, int idNguoiDung);
}