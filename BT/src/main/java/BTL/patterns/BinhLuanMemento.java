package BTL.patterns;

import BTL.models.BinhLuan;

public class BinhLuanMemento {
    private BinhLuan binhLuan;
    
    public BinhLuanMemento(BinhLuan binhLuan) {
        this.binhLuan = new BinhLuan();
        this.binhLuan.setId(binhLuan.getId());
        this.binhLuan.setIdNguoiDung(binhLuan.getIdNguoiDung());
        this.binhLuan.setIdSanPham(binhLuan.getIdSanPham());
        this.binhLuan.setNoiDung(binhLuan.getNoiDung());
        this.binhLuan.setNgayTao(binhLuan.getNgayTao());
        this.binhLuan.setNgayCapNhat(binhLuan.getNgayCapNhat());
    }
    
    public BinhLuan getBinhLuan() {
        return binhLuan;
    }
}