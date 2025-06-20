package BTL.controllers;

import BTL.daos.*;
import BTL.models.*;
import BTL.patterns.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/quan-ly-gio-hang")
public class QuanLyGioHangServlet extends HttpServlet {
    private final ThongTinGioHangDAO thongTinDAO = new ThongTinGioHangDAO();
    //private final ChiTietGioHangDAO chiTietDAO = new ChiTietGioHangDAO();
    private final GioHangChiTietDAO chiTietDAO = new GioHangChiTietDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<ThongTinGioHangTT> danhSachChiTiet = thongTinDAO.layThongTinChiTietGioHang();
        req.setAttribute("danhSachChiTiet", danhSachChiTiet);
        req.getRequestDispatcher("/views/giohang/quanLyGioHang.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("cap-nhat-so-luong".equals(action)) {
            int idCT = Integer.parseInt(req.getParameter("idGioHangChiTiet"));
            int soLuongMoi = Integer.parseInt(req.getParameter("soLuongMoi"));
            chiTietDAO.capNhatSoLuong(idCT, soLuongMoi);

        } 
        else if ("cap-nhat-trang-thai".equals(action)) {
            int idCT = Integer.parseInt(req.getParameter("idGioHangChiTiet"));
            String trangThaiMoi = req.getParameter("trangThaiMoi");
            int soLuongMoi = Integer.parseInt(req.getParameter("soLuongMoi")); // Lấy cả số lượng nếu có

            chiTietDAO.capNhatTrangThaiVaSoLuong(idCT, trangThaiMoi, soLuongMoi);  // Bạn cần viết thêm hàm này trong DAO
        }

        else if ("xoa-chi-tiet".equals(action)) {
            int idCT = Integer.parseInt(req.getParameter("idGioHangChiTiet"));
            chiTietDAO.xoaChiTiet(idCT);
        }

        res.sendRedirect(req.getContextPath() + "/quan-ly-gio-hang");
    }
}
