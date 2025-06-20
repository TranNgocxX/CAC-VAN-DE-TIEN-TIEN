package BTL.models;

import java.util.Date;

public class GioHangChiTiet {
    private int id;
    private int idGioHang;
    private SanPham sanPham;
    private int soLuong;
    private Date ngayThem;

    public GioHangChiTiet() {}

    public GioHangChiTiet(int id, int idGioHang, SanPham sanPham, int soLuong, Date ngayThem) {
        this.id = id;
        this.idGioHang = idGioHang;
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.ngayThem = ngayThem;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdGioHang() { return idGioHang; }
    public void setIdGioHang(int idGioHang) { this.idGioHang = idGioHang; }
    public SanPham getSanPham() { return sanPham; }
    public void setSanPham(SanPham sanPham) { this.sanPham = sanPham; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public Date getNgayThem() { return ngayThem; }
    public void setNgayThem(Date ngayThem) { this.ngayThem = ngayThem; }
}