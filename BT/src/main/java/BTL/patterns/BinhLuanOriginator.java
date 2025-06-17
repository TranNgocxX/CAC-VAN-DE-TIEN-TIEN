package BTL.patterns;

import BTL.models.BinhLuan;

public class BinhLuanOriginator {
    private BinhLuan binhLuan;
    
    public void setBinhLuan(BinhLuan binhLuan) {
        this.binhLuan = binhLuan;
    }
    
    public BinhLuanMemento luuTrangThai() {
        return new BinhLuanMemento(binhLuan);
    }
    
    public void phucHoiTrangThai(BinhLuanMemento memento) {
        this.binhLuan = memento.getBinhLuan();
    }
    
    public BinhLuan getBinhLuan() {
        return binhLuan;
    }
}