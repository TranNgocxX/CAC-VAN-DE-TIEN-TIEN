//package BTL.models;
//
//import java.util.Date;
//
//public class SanPham {
//    private int id;
//    private String tenSanPham;
//    private String moTa;
//    private double gia;
//    private int soLuong;
//    private String anhUrl;
//    private int idLoai;
//    private Date ngayTao;
//    private Date ngayCapNhat;
//
//    public SanPham() {}
//
//    public SanPham(String tenSanPham, String moTa, double gia, int soLuong, String anhUrl, int idLoai) {
//        this.tenSanPham = tenSanPham;
//        this.moTa = moTa;
//        this.gia = gia;
//        this.soLuong = soLuong;
//        this.anhUrl = anhUrl;
//        this.idLoai = idLoai;
//        this.ngayTao = new Date();
//        this.ngayCapNhat = new Date();
//    }
//
//    // Getters và Setters
//    public int getId() { return id; }
//    public void setId(int id) { this.id = id; }
//    public String getTenSanPham() { return tenSanPham; }
//    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
//    public String getMoTa() { return moTa; }
//    public void setMoTa(String moTa) { this.moTa = moTa; }
//    public double getGia() { return gia; }
//    public void setGia(double gia) { this.gia = gia; }
//    public int getSoLuong() { return soLuong; }
//    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
//    public String getAnhUrl() { return anhUrl; }
//    public void setAnhUrl(String anhUrl) { this.anhUrl = anhUrl; }
//    public int getIdLoai() { return idLoai; }
//    public void setIdLoai(int idLoai) { this.idLoai = idLoai; }
//    public Date getNgayTao() { return ngayTao; }
//    public void setNgayTao(Date ngayTao) { this.ngayTao = ngayTao; }
//    public Date getNgayCapNhat() { return ngayCapNhat; }
//    public void setNgayCapNhat(Date ngayCapNhat) { this.ngayCapNhat = ngayCapNhat; }
//}

package BTL.models;

import java.sql.Timestamp;

public class SanPham {
    private int id;
    private String tenSanPham;
    private String moTa;
    private double gia;
    private int soLuong;
    private String anhUrl;
    private int idLoai;
    private Timestamp ngayTao;
    private Timestamp ngayCapNhat;

    private SanPham(SanPhamBuilder builder) {
        this.id = builder.id;
        this.tenSanPham = builder.tenSanPham;
        this.moTa = builder.moTa;
        this.gia = builder.gia;
        this.soLuong = builder.soLuong;
        this.anhUrl = builder.anhUrl;
        this.idLoai = builder.idLoai;
        this.ngayTao = builder.ngayTao;
        this.ngayCapNhat = builder.ngayCapNhat;
    }

    // Getters
    public int getId() { return id; }
    public String getTenSanPham() { return tenSanPham; }
    public String getMoTa() { return moTa; }
    public double getGia() { return gia; }
    public int getSoLuong() { return soLuong; }
    public String getAnhUrl() { return anhUrl; }
    public int getIdLoai() { return idLoai; }
    public Timestamp getNgayTao() { return ngayTao; }
    public Timestamp getNgayCapNhat() { return ngayCapNhat; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public void setGia(double gia) { this.gia = gia; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public void setAnhUrl(String anhUrl) { this.anhUrl = anhUrl; }
    public void setIdLoai(int idLoai) { this.idLoai = idLoai; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }
    public void setNgayCapNhat(Timestamp ngayCapNhat) { this.ngayCapNhat = ngayCapNhat; }

    public static class SanPhamBuilder {
        private int id;
        private String tenSanPham;
        private String moTa;
        private double gia;
        private int soLuong;
        private String anhUrl;
        private int idLoai;
        private Timestamp ngayTao;
        private Timestamp ngayCapNhat;

        public SanPhamBuilder setId(int id) { this.id = id; return this; }
        public SanPhamBuilder setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; return this; }
        public SanPhamBuilder setMoTa(String moTa) { this.moTa = moTa; return this; }
        public SanPhamBuilder setGia(double gia) { this.gia = gia; return this; }
        public SanPhamBuilder setSoLuong(int soLuong) { this.soLuong = soLuong; return this; }
        public SanPhamBuilder setAnhUrl(String anhUrl) { this.anhUrl = anhUrl; return this; }
        public SanPhamBuilder setIdLoai(int idLoai) { this.idLoai = idLoai; return this; }
        public SanPhamBuilder setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; return this; }
        public SanPhamBuilder setNgayCapNhat(Timestamp ngayCapNhat) { this.ngayCapNhat = ngayCapNhat; return this; }

        public SanPham build() { return new SanPham(this); }
    }
}