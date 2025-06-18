package BTL.models;

import java.sql.Timestamp;

public class BinhLuan {
    private int id;
    private int idNguoiDung;
    private int idSanPham;
    private String noiDung;
    private Timestamp ngayTao;
    private Timestamp ngayCapNhat;

    private NguoiDung nguoiDung; 

    public BinhLuan() {}
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdNguoiDung() { return idNguoiDung; }
    public void setIdNguoiDung(int idNguoiDung) { this.idNguoiDung = idNguoiDung; }

    public int getIdSanPham() { return idSanPham; }
    public void setIdSanPham(int idSanPham) { this.idSanPham = idSanPham; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }

    public Timestamp getNgayCapNhat() { return ngayCapNhat; }
    public void setNgayCapNhat(Timestamp ngayCapNhat) { this.ngayCapNhat = ngayCapNhat; }

    public NguoiDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(NguoiDung nguoiDung) { this.nguoiDung = nguoiDung; }
}

