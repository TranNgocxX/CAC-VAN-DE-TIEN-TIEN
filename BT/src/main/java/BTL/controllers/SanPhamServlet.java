package BTL.controllers;

import BTL.models.LoaiSanPham;
import BTL.models.SanPham;
import BTL.patterns.QuanLySanPhamFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/san-pham/*")
public class SanPhamServlet extends HttpServlet {
    private QuanLySanPhamFacade sanPhamFacade = new QuanLySanPhamFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null || action.equals("/quan-ly")) {
            hienThiDanhSachSanPham(request, response);

        } else if (action.equals("/them")) {
            request.setAttribute("sp", new SanPham());

            List<LoaiSanPham> danhSachLoai = new BTL.daos.LoaiSanPhamDAO().layTatCa();
            request.setAttribute("danhSachLoai", danhSachLoai);

            request.getRequestDispatcher("/views/sanpham/themHoacSuaSanPham.jsp").forward(request, response);

        } else if (action.equals("/sua")) {
            int id = Integer.parseInt(request.getParameter("id"));
            SanPham sp = sanPhamFacade.timTheoId(id);
            request.setAttribute("sp", sp);

            List<LoaiSanPham> danhSachLoai = new BTL.daos.LoaiSanPhamDAO().layTatCa();
            request.setAttribute("danhSachLoai", danhSachLoai);

            request.getRequestDispatcher("/views/sanpham/themHoacSuaSanPham.jsp").forward(request, response);

        } else if (action.equals("/xoa")) {
            int id = Integer.parseInt(request.getParameter("id"));
            sanPhamFacade.xoaSanPham(id);
            response.sendRedirect(request.getContextPath() + "/san-pham/quan-ly");

        } else if (action.equals("/chi-tiet")) {
            int id = Integer.parseInt(request.getParameter("id"));
            SanPham sp = sanPhamFacade.timTheoId(id);

            if (sp == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            request.setAttribute("sp", sp);
            request.getRequestDispatcher("/views/sanpham/chiTietSanPham.jsp").forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        String ten = request.getParameter("tenSanPham");
        String moTa = request.getParameter("moTa");
        double gia = Double.parseDouble(request.getParameter("gia"));
        int soLuong = Integer.parseInt(request.getParameter("soLuong"));
        String anhUrl = request.getParameter("anhUrl");
        int idLoai = Integer.parseInt(request.getParameter("idLoai"));

        SanPham sp = new SanPham();
        sp.setId(id);
        sp.setTenSanPham(ten);
        sp.setMoTa(moTa);
        sp.setGia(gia);
        sp.setSoLuong(soLuong);
        sp.setAnhUrl(anhUrl);
        sp.setIdLoai(idLoai);
        sp.setNgayCapNhat(new Timestamp(System.currentTimeMillis()));

        if (id == 0) {
            sp.setNgayTao(sp.getNgayCapNhat());
        } else {
            sp.setNgayTao(sanPhamFacade.timTheoId(id).getNgayTao());
        }

        sanPhamFacade.luuSanPham(sp);
        response.sendRedirect(request.getContextPath() + "/san-pham/quan-ly");
    }

    private void hienThiDanhSachSanPham(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<SanPham> danhSachSanPham = sanPhamFacade.layTatCaSanPham();
        request.setAttribute("danhSachSanPham", danhSachSanPham);
        request.getRequestDispatcher("/views/sanpham/quanLySanPham.jsp").forward(request, response);
    }
}
