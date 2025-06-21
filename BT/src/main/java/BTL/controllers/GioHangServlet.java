package BTL.controllers;

import BTL.patterns.DAOFactory;
import BTL.daos.GioHangDAO;
import BTL.daos.GioHangChiTietDAO;
import BTL.daos.SanPhamDAO;
import BTL.models.GioHang;
import BTL.models.GioHangChiTiet;
import BTL.models.NguoiDung;
import BTL.models.SanPham;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/gio-hang")
public class GioHangServlet extends HttpServlet {
    private GioHangDAO gioHangDAO = DAOFactory.getGioHangDAO();
    private GioHangChiTietDAO gioHangChiTietDAO = DAOFactory.getGioHangChiTietDAO();
    private SanPhamDAO sanPhamDAO = DAOFactory.getSanPhamDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (nguoiDung == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\": false, \"message\": \"Vui lòng đăng nhập.\"}");
            return;
        }

        try {
            int idSanPham = Integer.parseInt(request.getParameter("idSanPham"));
            int soLuong = Integer.parseInt(request.getParameter("soLuong"));

            // Kiểm tra sản phẩm
            SanPham sp = sanPhamDAO.timTheoId(idSanPham);
            if (sp == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"message\": \"Sản phẩm không tồn tại.\"}");
                return;
            }
            if (soLuong <= 0 || soLuong > sp.getSoLuong()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"message\": \"Số lượng không hợp lệ.\"}");
                return;
            }

            // Tìm / tạo giỏ hàng
            GioHang gioHang = gioHangDAO.timTheoNguoiDung(nguoiDung.getId());
            if (gioHang == null) {
                gioHang = gioHangDAO.taoGioHang(nguoiDung.getId());
                if (gioHang == null) {
                    throw new SQLException("Không thể tạo giỏ hàng.");
                }
            }

            // Thêm / cập nhật chi tiết giỏ hàng
            GioHangChiTiet chiTiet = new GioHangChiTiet();
            chiTiet.setIdGioHang(gioHang.getId());
            chiTiet.setSanPham(sp);
            chiTiet.setSoLuong(soLuong);
            chiTiet.setTrangThai("dang_mua");
            gioHangChiTietDAO.themHoacCapNhat(chiTiet);

            // Cập nhật session
            List<GioHangChiTiet> gioHangList = gioHangChiTietDAO.layTheoGioHang(gioHang.getId());
            session.setAttribute("giohang", gioHangList);

            response.getWriter().write("{\"success\": true, \"message\": \"Thêm vào giỏ hàng thành công.\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"Dữ liệu không hợp lệ.\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi cơ sở dữ liệu: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Đã có lỗi xảy ra: " + e.getMessage() + "\"}");
        }
    }
}