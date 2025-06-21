package BTL.controllers;

import BTL.daos.BinhLuanDAO;
import BTL.models.BinhLuan;
import BTL.models.LoaiSanPham;
import BTL.models.SanPham;
import BTL.services.QuanLySanPhamService;
import BTL.patterns.SanPhamFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/san-pham/*")
public class SanPhamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private QuanLySanPhamService sanPhamService = new QuanLySanPhamService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null || action.equals("/quan-ly")) {
            hienThiDanhSachSanPham(request, response);

        } else if (action.equals("/them")) {
            request.setAttribute("sp", SanPhamFactory.taoSanPhamMacDinh(1)); // Mặc định idLoai=1
            List<LoaiSanPham> danhSachLoai = new BTL.daos.LoaiSanPhamDAO().layTatCa();
            request.setAttribute("danhSachLoai", danhSachLoai);
            request.getRequestDispatcher("/views/sanpham/themHoacSuaSanPham.jsp").forward(request, response);

        } else if (action.equals("/sua")) {
            int id = Integer.parseInt(request.getParameter("id"));
            SanPham sp = sanPhamService.timTheoId(id);
            if (sp == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("sp", sp);
            List<LoaiSanPham> danhSachLoai = new BTL.daos.LoaiSanPhamDAO().layTatCa();
            request.setAttribute("danhSachLoai", danhSachLoai);
            request.getRequestDispatcher("/views/sanpham/themHoacSuaSanPham.jsp").forward(request, response);

        } else if (action.equals("/xoa")) {
            int id = Integer.parseInt(request.getParameter("id"));
            sanPhamService.xoaSanPham(id);
            response.sendRedirect(request.getContextPath() + "/san-pham/quan-ly");

        } else if (action.equals("/chi-tiet")) {
            int id = Integer.parseInt(request.getParameter("id"));
            SanPham sp = sanPhamService.timTheoId(id);
            if (sp == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            List<BinhLuan> danhSachBinhLuan = new BinhLuanDAO().layTheoSanPham(id);
            request.setAttribute("danhSachBinhLuan", danhSachBinhLuan);
            request.setAttribute("sp", sp);
            request.getRequestDispatcher("/views/sanpham/chiTietSanPham.jsp").forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0;
            String ten = request.getParameter("tenSanPham");
            String moTa = request.getParameter("moTa");
            String giaStr = request.getParameter("gia");
            String soLuongStr = request.getParameter("soLuong");
            String anhUrl = request.getParameter("anhUrl");
            String idLoaiStr = request.getParameter("idLoai");
            String phanTramGiamStr = request.getParameter("phanTramGiam");

            if (ten == null || moTa == null || giaStr == null || soLuongStr == null || idLoaiStr == null) {
                request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin.");
                request.getRequestDispatcher("/views/sanpham/themHoacSuaSanPham.jsp").forward(request, response);
                return;
            }

            double gia = Double.parseDouble(giaStr);
            int soLuong = Integer.parseInt(soLuongStr);
            int idLoai = Integer.parseInt(idLoaiStr);
            double phanTramGiam = phanTramGiamStr != null && !phanTramGiamStr.isEmpty() ? 
                                 Double.parseDouble(phanTramGiamStr) : 0.0;

            SanPham sp;
            try {
                if (id == 0) {
                    if (phanTramGiam > 0) {
                        sp = SanPhamFactory.taoSanPhamKhuyenMai(ten, moTa, gia, soLuong, anhUrl, idLoai, phanTramGiam);
                    } else {
                        sp = SanPhamFactory.taoSanPhamMoi(ten, moTa, gia, soLuong, anhUrl, idLoai);
                    }
                } else {
                    SanPham existingSp = sanPhamService.timTheoId(id);
                    if (existingSp == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
                    sp = SanPhamFactory.capNhatSanPham(existingSp, ten, moTa, gia, soLuong, anhUrl, idLoai);
                }
                sanPhamService.luuSanPham(sp);
                response.sendRedirect(request.getContextPath() + "/san-pham/quan-ly");
            } catch (IllegalArgumentException e) {
                request.setAttribute("errorMessage", e.getMessage());
                request.getRequestDispatcher("/views/sanpham/themHoacSuaSanPham.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Dữ liệu không hợp lệ (giá, số lượng, hoặc loại).");
            request.getRequestDispatcher("/views/sanpham/themHoacSuaSanPham.jsp").forward(request, response);
        }
    }

    private void hienThiDanhSachSanPham(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<SanPham> danhSachSanPham = sanPhamService.layTatCaSanPham();
        request.setAttribute("danhSachSanPham", danhSachSanPham);
        request.getRequestDispatcher("/views/sanpham/quanLySanPham.jsp").forward(request, response);
    }
}
