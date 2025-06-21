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

   public BinhLuan() {
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getIdNguoiDung() {
      return this.idNguoiDung;
   }

   public void setIdNguoiDung(int idNguoiDung) {
      this.idNguoiDung = idNguoiDung;
   }

   public int getIdSanPham() {
      return this.idSanPham;
   }

   public void setIdSanPham(int idSanPham) {
      this.idSanPham = idSanPham;
   }

   public String getNoiDung() {
      return this.noiDung;
   }

   public void setNoiDung(String noiDung) {
      this.noiDung = noiDung;
   }

   public Timestamp getNgayTao() {
      return this.ngayTao;
   }

   public void setNgayTao(Timestamp ngayTao) {
      this.ngayTao = ngayTao;
   }

   public Timestamp getNgayCapNhat() {
      return this.ngayCapNhat;
   }

   public void setNgayCapNhat(Timestamp ngayCapNhat) {
      this.ngayCapNhat = ngayCapNhat;
   }

   public NguoiDung getNguoiDung() {
      return this.nguoiDung;
   }

   public void setNguoiDung(NguoiDung nguoiDung) {
      this.nguoiDung = nguoiDung;
   }
}