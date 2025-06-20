package BTL.controllers;

import BTL.daos.GioHangDAO;
import BTL.daos.GioHangChiTietDAO;
import BTL.daos.SanPhamDAO;
import BTL.models.GioHang;
import BTL.models.NguoiDung;
import BTL.models.SanPham;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/gio-hang")
public class GioHangServlet extends HttpServlet {
    private GioHangDAO gioHangDAO;
    private GioHangChiTietDAO gioHangChiTietDAO;
    private SanPhamDAO sanPhamDAO;

    @Override
    public void init() throws ServletException {
        gioHangDAO = new GioHangDAO();
        gioHangChiTietDAO = new GioHangChiTietDAO();
        sanPhamDAO = new SanPhamDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");

        // Chưa đăng nhập
        if (nguoiDung == null) {
            response.sendRedirect(request.getContextPath() + "/auth/dang-nhap");
            return;
        }

        try {
            int idSanPham = Integer.parseInt(request.getParameter("idSanPham"));
            int soLuong = Integer.parseInt(request.getParameter("soLuong"));

            // Lấy sản phẩm
            SanPham sanPham = sanPhamDAO.laySanPhamTheoId(idSanPham);
            if (sanPham == null) {
                request.setAttribute("error", "Sản phẩm không tồn tại.");
                request.getRequestDispatcher("/chiTietSanPham.jsp").forward(request, response);
                return;
            }

            // Kiểm tra tồn kho
            if (soLuong <= 0 || soLuong > sanPham.getSoLuong()) {
                request.setAttribute("error", "Số lượng yêu cầu không hợp lệ hoặc vượt quá tồn kho.");
                request.setAttribute("sanPham", sanPham); // Gửi lại dữ liệu nếu cần
                request.getRequestDispatcher("/chiTietSanPham.jsp").forward(request, response);
                return;
            }

            // Lấy hoặc tạo giỏ hàng
            GioHang gioHang = gioHangDAO.layHoacTaoGioHang(nguoiDung.getId());

            if (gioHang == null) {
                request.setAttribute("error", "Không thể tạo giỏ hàng.");
                request.getRequestDispatcher("/chiTietSanPham.jsp").forward(request, response);
                return;
            }

            // Thêm hoặc cập nhật chi tiết
            gioHangChiTietDAO.themHoacCapNhatSanPham(gioHang.getId(), idSanPham, soLuong);

            // Cập nhật lại giỏ hàng trong session
            gioHang.setDanhSachSanPham(gioHangChiTietDAO.layDanhSachSanPhamTheoGioHang(gioHang.getId()));
            session.setAttribute("giohang", gioHang.getDanhSachSanPham());

            // Thông báo thành công
            session.setAttribute("success", "Đã thêm sản phẩm vào giỏ hàng!");
            response.sendRedirect(request.getContextPath() + "/chi-tiet-san-pham?id=" + idSanPham);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ.");
            request.getRequestDispatcher("/chiTietSanPham.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi
            request.setAttribute("error", "Có lỗi xảy ra khi thêm vào giỏ hàng.");
            request.getRequestDispatcher("/chiTietSanPham.jsp").forward(request, response);
        }
    }
}
