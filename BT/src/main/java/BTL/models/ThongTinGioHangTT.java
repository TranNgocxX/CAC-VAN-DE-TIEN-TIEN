package BTL.models;

import java.util.Date;

public class ThongTinGioHangTT {
    private int idChiTiet;
    private String tenNguoiDung;
    private String tenSanPham;
    private String tenLoai;
    private int soLuong;
    private double gia;
    private double tongTien;
    private Date ngayThem;
    private String trangThai;

    // Getters + Setters
    public int getIdChiTiet() { return idChiTiet; }
    public void setIdChiTiet(int idChiTiet) { this.idChiTiet = idChiTiet; }
    public String getTenNguoiDung() { return tenNguoiDung; }
    public void setTenNguoiDung(String tenNguoiDung) { this.tenNguoiDung = tenNguoiDung; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public String getTenLoai() { return tenLoai; }
    public void setTenLoai(String tenLoai) { this.tenLoai = tenLoai; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public Date getNgayThem() { return ngayThem; }
    public void setNgayThem(Date ngayThem) { this.ngayThem = ngayThem; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}