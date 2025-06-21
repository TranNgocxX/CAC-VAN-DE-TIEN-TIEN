package BTL.patterns;

import BTL.daos.GioHangChiTietDAO;
import BTL.daos.GioHangDAO;

public class GioHangFactory {
    public static GioHangDAO getGioHangDAO() {
        return new GioHangDAO();
    }
    
    public static GioHangChiTietDAO getGioHangChiTietDAO() {
    	return new GioHangChiTietDAO();
    }
    
}


