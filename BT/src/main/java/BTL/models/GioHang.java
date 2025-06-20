package BTL.models;

import java.util.*;

public class GioHang {
    private int id;
    private int idNguoiDung;
    private String trangThai;
    private Date ngayTao;
    private List<GioHangChiTiet> danhSachSanPham;

    public GioHang() {}

    public GioHang(int id, int idNguoiDung, String trangThai, Date ngayTao) {
        this.id = id;
        this.idNguoiDung = idNguoiDung;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.danhSachSanPham = new ArrayList<>();
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdNguoiDung() { return idNguoiDung; }
    public void setIdNguoiDung(int idNguoiDung) { this.idNguoiDung = idNguoiDung; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public Date getNgayTao() { return ngayTao; }
    public void setNgayTao(Date ngayTao) { this.ngayTao = ngayTao; }
    public List<GioHangChiTiet> getDanhSachSanPham() { return danhSachSanPham; }
    public void setDanhSachSanPham(List<GioHangChiTiet> danhSachSanPham) { this.danhSachSanPham = danhSachSanPham; }
}