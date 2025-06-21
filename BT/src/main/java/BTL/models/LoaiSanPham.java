package BTL.models;

public class LoaiSanPham {
    private int id;
    private String tenLoai;

    public LoaiSanPham() {}

    public LoaiSanPham(int id, String tenLoai) {
        this.id = id;
        this.tenLoai = tenLoai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
