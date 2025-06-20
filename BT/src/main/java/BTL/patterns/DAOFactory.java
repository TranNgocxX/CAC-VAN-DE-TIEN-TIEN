package BTL.patterns;

import BTL.daos.*;

public class DAOFactory {
    public static SanPhamDAO createSanPhamDAO() {
        return new SanPhamDAO();
    }

    public static GioHangDAO getGioHangDAO() {
        return new GioHangDAO();
    }

    public static ChiTietGioHangDAO getChiTietGioHangDAO() {
        return new ChiTietGioHangDAO();
    }
}
