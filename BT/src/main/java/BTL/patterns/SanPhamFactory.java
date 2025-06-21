package BTL.patterns;

import BTL.models.SanPham;
import java.sql.Timestamp;

public class SanPhamFactory {
    // Tạo sp mới với đầy đủ tt 
	public static SanPham taoSanPhamMoi(String ten, String moTa, double gia, int soLuong,
  String anhUrl, int idLoai) {
		if (ten == null || ten.isEmpty()) {
		throw new IllegalArgumentException("Tên sản phẩm không được rỗng");
		}
		if (gia <= 0) {
		throw new IllegalArgumentException("Giá phải lớn hơn 0");
		}
		if (soLuong <= 0) {
		throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
		}
		Timestamp now = new Timestamp(System.currentTimeMillis());
		SanPham sp = new SanPham();
		sp.setTenSanPham(ten);
		sp.setMoTa(moTa != null ? moTa : "");
		sp.setGia(gia);
		sp.setSoLuong(soLuong);
		sp.setAnhUrl(anhUrl != null ? anhUrl : "");
		sp.setIdLoai(idLoai);
		sp.setNgayTao(now);
		sp.setNgayCapNhat(now);
		return sp;
		}


    // Tạo sp mặc định cho 1 loại sp cụ thể 
    public static SanPham taoSanPhamMacDinh(int idLoai) {
        validateIdLoai(idLoai);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SanPham sp = new SanPham();
        sp.setTenSanPham("Sản phẩm mặc định");
        sp.setMoTa("Mô tả mặc định");
        sp.setGia(1.0);
        sp.setSoLuong(1);
        sp.setAnhUrl("");
        sp.setIdLoai(idLoai);
        sp.setNgayTao(now);
        sp.setNgayCapNhat(now);
        return sp;
    }

    // Tạo sp khuyến mãi với giá giảm
    public static SanPham taoSanPhamKhuyenMai(String ten, String moTa, double giaGoc, 
                                             int soLuong, String anhUrl, int idLoai, double phanTramGiam) {
        if (phanTramGiam < 0 || phanTramGiam > 100) {
            throw new IllegalArgumentException("Phần trăm giảm giá phải từ 0 đến 100.");
        }
        double giaKhuyenMai = giaGoc * (1 - phanTramGiam / 100);
        return taoSanPhamMoi(ten, moTa + " (Khuyến mãi " + phanTramGiam + "%)", 
                            giaKhuyenMai, soLuong, anhUrl, idLoai);
    }

    // Cập nhật thông tin sp  
    public static SanPham capNhatSanPham(SanPham existingSp, String ten, String moTa, 
                                        double gia, int soLuong, String anhUrl, int idLoai) {
        validateInput(ten, gia, soLuong, idLoai);
        
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SanPham sp = new SanPham();
        sp.setId(existingSp.getId());
        sp.setTenSanPham(ten);
        sp.setMoTa(moTa != null ? moTa : "");
        sp.setGia(gia);
        sp.setSoLuong(soLuong);
        sp.setAnhUrl(anhUrl != null ? anhUrl : "");
        sp.setIdLoai(idLoai);
        sp.setNgayTao(existingSp.getNgayTao()); 
        sp.setNgayCapNhat(now);
        return sp;
    }

    // Ktra dl đầu vào
    private static void validateInput(String ten, double gia, int soLuong, int idLoai) {
        if (ten == null || ten.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không được rỗng.");
        }
        if (gia <= 0) {
            throw new IllegalArgumentException("Giá phải lớn hơn 0.");
        }
        if (soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn hoặc bằng 0.");
        }
        if (idLoai <= 0) {
            throw new IllegalArgumentException("ID loại sản phẩm phải hợp lệ.");
        }
    }

    // Kiểm tra ID loại sản phẩm
    private static void validateIdLoai(int idLoai) {
        if (idLoai <= 0) {
            throw new IllegalArgumentException("ID loại sản phẩm phải hợp lệ.");
        }
    }
}

