package BTL.patterns;

import BTL.daos.GioHangDAO;

public class GioHangFactory {
    public static GioHangDAO getGioHangDAO() {
        return new GioHangDAO();
    }
}
