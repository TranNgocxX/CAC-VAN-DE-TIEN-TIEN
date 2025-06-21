package BTL.controllers;

import BTL.daos.*;
import BTL.models.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/quan-ly-gio-hang")
public class QuanLyGioHangServlet extends HttpServlet {
    private final ThongTinGioHangDAO thongTinDAO = new ThongTinGioHangDAO();
    private final GioHangChiTietDAO chiTietDAO = new GioHangChiTietDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("chinh-sua".equals(action)) {
            try {
                int idCT = Integer.parseInt(req.getParameter("idGioHangChiTiet"));
                ThongTinGioHangTT chiTiet = thongTinDAO.layChiTietTheoId(idCT);
                if (chiTiet != null) {
                    req.setAttribute("chiTiet", chiTiet);
                } else {
                    req.setAttribute("error", "Không tìm thấy chi tiết giỏ hàng.");
                }
            } catch (NumberFormatException e) {
                req.setAttribute("error", "ID chi tiết không hợp lệ.");
            }
        }
        List<ThongTinGioHangTT> danhSachChiTiet = thongTinDAO.layThongTinChiTietGioHang();
        req.setAttribute("danhSachChiTiet", danhSachChiTiet);
        req.getRequestDispatcher("/views/giohang/quanLyGioHang.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("cap-nhat-so-luong".equals(action)) {
                int idCT = Integer.parseInt(req.getParameter("idGioHangChiTiet"));
                int soLuongMoi = Integer.parseInt(req.getParameter("soLuongMoi"));
                if (soLuongMoi < 1) throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
                chiTietDAO.capNhatChiTietGioHang(idCT, soLuongMoi, null);
            } else if ("cap-nhat-trang-thai".equals(action)) {
                String idCTParam = req.getParameter("idGioHangChiTiet");
                String soLuongMoiParam = req.getParameter("soLuongMoi");
                String trangThaiMoi = req.getParameter("trangThaiMoi");
                if (idCTParam == null || soLuongMoiParam == null || trangThaiMoi == null) {
                    throw new IllegalArgumentException("Thiếu thông tin cần thiết.");
                }
                int idCT = Integer.parseInt(idCTParam);
                int soLuongMoi = Integer.parseInt(soLuongMoiParam);
                if (soLuongMoi < 1) throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
                if (!Arrays.asList("dang_mua", "da_dat_hang", "da_huy").contains(trangThaiMoi)) {
                    throw new IllegalArgumentException("Trạng thái không hợp lệ.");
                }
                chiTietDAO.capNhatChiTietGioHang(idCT, soLuongMoi, trangThaiMoi);
            } else if ("xoa-chi-tiet".equals(action)) {
                int idCT = Integer.parseInt(req.getParameter("idGioHangChiTiet"));
                chiTietDAO.xoaChiTiet(idCT);
            } else {
                throw new IllegalArgumentException("Hành động không hợp lệ.");
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Lỗi: Dữ liệu nhập không hợp lệ (ID hoặc số lượng không phải số).");
            List<ThongTinGioHangTT> danhSachChiTiet = thongTinDAO.layThongTinChiTietGioHang();
            req.setAttribute("danhSachChiTiet", danhSachChiTiet);
            req.getRequestDispatcher("/views/giohang/quanLyGioHang.jsp").forward(req, res);
            return;
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", "Lỗi: " + e.getMessage());
            List<ThongTinGioHangTT> danhSachChiTiet = thongTinDAO.layThongTinChiTietGioHang();
            req.setAttribute("danhSachChiTiet", danhSachChiTiet);
            req.getRequestDispatcher("/views/giohang/quanLyGioHang.jsp").forward(req, res);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Đã xảy ra lỗi hệ thống: " + e.getMessage());
            List<ThongTinGioHangTT> danhSachChiTiet = thongTinDAO.layThongTinChiTietGioHang();
            req.setAttribute("danhSachChiTiet", danhSachChiTiet);
            req.getRequestDispatcher("/views/giohang/quanLyGioHang.jsp").forward(req, res);
            return;
        }
        res.sendRedirect(req.getContextPath() + "/quan-ly-gio-hang");
    }
}
