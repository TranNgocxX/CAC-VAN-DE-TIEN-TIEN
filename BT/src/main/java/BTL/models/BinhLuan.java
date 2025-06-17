package BTL.models;

import java.util.Date;

public class BinhLuan {
    private int id;
    private int idNguoiDung;
    private int idSanPham;
    private String noiDung;
    private Date ngayTao;
    private Date ngayCapNhat;
    
    // Constructor, getter và setter
    public BinhLuan() {}
    
    public BinhLuan(int idNguoiDung, int idSanPham, String noiDung) {
        this.idNguoiDung = idNguoiDung;
        this.idSanPham = idSanPham;
        this.noiDung = noiDung;
        this.ngayTao = new Date();
        this.ngayCapNhat = new Date();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdNguoiDung() {
        return idNguoiDung;
    }
    
    public void setIdNguoiDung(int idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }
    
    public int getIdSanPham() {
        return idSanPham;
    }
    
    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }
    
    public String getNoiDung() {
        return noiDung;
    }
    
    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
    
    public Date getNgayTao() {
        return ngayTao;
    }
    
    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
    
    public Date getNgayCapNhat() {
        return ngayCapNhat;
    }
    
    public void setNgayCapNhat(Date ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }
}