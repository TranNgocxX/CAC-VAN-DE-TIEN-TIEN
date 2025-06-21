package BTL.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GioHang {
    private int id;
    private int idNguoiDung;
    private String trangThai;
    private Date ngayTao;
    private List<GioHangChiTiet> danhSachSanPham;

    public GioHang() {
    }

    public GioHang(int id, int idNguoiDung, String trangThai, Date ngayTao) {
        this.id = id;
        this.idNguoiDung = idNguoiDung;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.danhSachSanPham = new ArrayList<>();
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getIdNguoiDung() {
        return idNguoiDung;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public List<GioHangChiTiet> getDanhSachSanPham() {
        return danhSachSanPham;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setIdNguoiDung(int idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public void setDanhSachSanPham(List<GioHangChiTiet> danhSachSanPham) {
        this.danhSachSanPham = danhSachSanPham;
    }
}