package BTL.patterns;

import java.util.List;

import BTL.daos.NguoiDungDAO;
import BTL.models.NguoiDung;

public class QuanLyNguoiDungFacade {
    private NguoiDungDAO nguoiDungDAO;
    
    public QuanLyNguoiDungFacade() {
        this.nguoiDungDAO = NguoiDungDAO.getInstance();
    }
    
    public boolean dangKy(NguoiDung nguoiDung) {
        if (nguoiDungDAO.timTheoTenDangNhap(nguoiDung.getTenDangNhap()) != null) {
            return false; // Tên đăng nhập đã tồn tại
        }
        return nguoiDungDAO.themNguoiDung(nguoiDung);
    }
    
    public NguoiDung dangNhap(String tenDangNhap, String matKhau) {
        NguoiDung nguoiDung = nguoiDungDAO.timTheoTenDangNhap(tenDangNhap);
        if (nguoiDung != null && nguoiDung.getMatKhau().equals(matKhau)) {
            return nguoiDung;
        }
        return null;
    }
    
    public NguoiDung layThongTinNguoiDung(int id) {
        return nguoiDungDAO.timTheoId(id);
    }
    
    public List<NguoiDung> layDanhSachNguoiDung() {
        return nguoiDungDAO.layTatCa();
    }
}