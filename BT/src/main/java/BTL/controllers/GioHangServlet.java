package BTL.controllers;

import BTL.daos.GioHangDAO;
import BTL.models.GioHangChiTiet;
import BTL.models.NguoiDung;
import BTL.models.SanPham;
import BTL.services.QuanLySanPhamService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/gio-hang")
public class GioHangServlet extends HttpServlet {
    private QuanLySanPhamService sanPhamService = new QuanLySanPhamService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("nguoiDung");
        if (nd != null) {
            GioHangDAO dao = new GioHangDAO();
            List<GioHangChiTiet> ds = dao.layTheoNguoiDung(nd.getId());
            session.setAttribute("danhSachGioHang", ds);

            double tong = ds.stream()
                .mapToDouble(i -> i.getSanPham().getGia() * i.getSoLuong())
                .sum();
            session.setAttribute("tongTien", tong);
        }
        request.getRequestDispatcher("/views/giohang/gioHang.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idSanPham = Integer.parseInt(request.getParameter("idSanPham"));
        int soLuong = Integer.parseInt(request.getParameter("soLuong"));

        SanPham sp = sanPhamService.timTheoId(idSanPham);
        if (sp == null) {
            response.sendRedirect(request.getContextPath() + "/san-pham");
            return;
        }

        HttpSession session = request.getSession();
        List<GioHangChiTiet> gioHang = (List<GioHangChiTiet>) session.getAttribute("giohang");
        if (gioHang == null) {
            gioHang = new ArrayList<>();
        }

        boolean daCoSanPham = false;
        for (GioHangChiTiet item : gioHang) {
            if (item.getSanPham().getId() == idSanPham) {
                item.setSoLuong(item.getSoLuong() + soLuong);
                daCoSanPham = true;
                break;
            }
        }

        if (!daCoSanPham) {
            GioHangChiTiet moi = new GioHangChiTiet();
            moi.setIdGioHang(0); // nếu cần ID giỏ hàng riêng
            moi.setSanPham(sp);
            moi.setSoLuong(soLuong);
            moi.setNgayThem(new Date());
            gioHang.add(moi);
        }

        // 👉 Cập nhật session
        session.setAttribute("danhSachGioHang", gioHang);

        // 👉 Tính tổng tiền để hiển thị
        double tongTien = 0;
        for (GioHangChiTiet item : gioHang) {
            tongTien += item.getSanPham().getGia() * item.getSoLuong();
        }
        session.setAttribute("tongTien", tongTien);

        // Quay lại trang chi tiết sản phẩm hoặc trang giỏ hàng
        response.sendRedirect(request.getContextPath() + "/san-pham/chi-tiet?id=" + idSanPham);
    }
}


//package BTL.controllers;
//
//import BTL.daos.GioHangDAO;
//import BTL.models.GioHangChiTiet;
//import BTL.patterns.DAOFactory;
//import java.io.IOException;
//import java.util.List;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@WebServlet("/gio-hang")
//public class GioHangServlet extends HttpServlet {
//    private GioHangDAO gioHangDAO = DAOFactory.getGioHangDAO();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        Integer idNguoiDung = (Integer) session.getAttribute("idNguoiDung");
//
//        if (idNguoiDung == null) {
//            response.sendRedirect(request.getContextPath() + "/auth/dang-nhap");
//            return;
//        }
//
//        // Lấy danh sách giỏ hàng và gán vào session
//        List<GioHangChiTiet> giohang = gioHangDAO.layTheoNguoiDung(idNguoiDung);
//        session.setAttribute("giohang", giohang);
//
//        // Chuyển hướng đến trang chính hoặc trang giỏ hàng
//        String redirectUrl = request.getParameter("redirect") != null ? request.getParameter("redirect") : "/trang-chu";
//        response.sendRedirect(request.getContextPath() + redirectUrl);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        Integer idNguoiDung = (Integer) session.getAttribute("idNguoiDung");
//
//        if (idNguoiDung == null) {
//            response.sendRedirect(request.getContextPath() + "/auth/dang-nhap");
//            return;
//        }
//
//        // Xử lý thêm sản phẩm vào giỏ hàng
//        String action = request.getParameter("action");
//        if ("add".equals(action)) {
//            Integer idSanPham = Integer.parseInt(request.getParameter("idSanPham"));
//            Integer soLuong = Integer.parseInt(request.getParameter("soLuong"));
//
//            if (idSanPham != null && soLuong != null) {
//                gioHangDAO.themVaoGio(idNguoiDung, idSanPham, soLuong);
//            }
//        }
//
//        // Cập nhật lại danh sách giỏ hàng trong session
//        List<GioHangChiTiet> giohang = gioHangDAO.layTheoNguoiDung(idNguoiDung);
//        session.setAttribute("giohang", giohang);
//
//        // Chuyển hướng về trang chi tiết sản phẩm hoặc trang chính
//        String redirectUrl = request.getParameter("redirect") != null ? request.getParameter("redirect") : "/trang-chu";
//        response.sendRedirect(request.getContextPath() + redirectUrl);
//    }
//}
