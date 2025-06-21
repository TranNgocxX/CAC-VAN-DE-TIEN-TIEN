package BTL.controllers;

import BTL.daos.LoaiSanPhamDAO;
import BTL.daos.SanPhamDAO;
import BTL.daos.GioHangDAO;
import BTL.models.GioHangChiTiet;
import BTL.models.LoaiSanPham;
import BTL.models.NguoiDung;
import BTL.models.SanPham;
import BTL.patterns.GioHangFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/trang-chu")
public class TrangChuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy id loại sp 
        String idLoaiStr = req.getParameter("idLoai");
        int idLoai = 0;
        try {
            idLoai = Integer.parseInt(idLoaiStr);
        } catch (Exception ignored) {}

        // Lấy DS sản phẩm và loại sản phẩm
        SanPhamDAO spDAO = new SanPhamDAO();
        LoaiSanPhamDAO loaiDAO = new LoaiSanPhamDAO();

        List<SanPham> dsSanPham = idLoai > 0 ? spDAO.layTheoLoai(idLoai) : spDAO.layTatCa();
        List<LoaiSanPham> dsLoai = loaiDAO.layTatCa();

        req.setAttribute("danhSachSanPham", dsSanPham);
        req.setAttribute("danhSachLoai", dsLoai);
        req.setAttribute("content", "/views/trangChu.jsp");

        // Lấy dữ liệu bảng gio_hang 
        HttpSession session = req.getSession();
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");

        if (nguoiDung != null) {
            GioHangDAO gioHangDAO = GioHangFactory.getGioHangDAO();
            List<GioHangChiTiet> danhSachGio = gioHangDAO.layTheoNguoiDung(nguoiDung.getId());

            session.setAttribute("danhSachGioHang", danhSachGio);

            double tongTien = 0;
            for (GioHangChiTiet item : danhSachGio) {
                tongTien += item.getSanPham().getGia() * item.getSoLuong();
            }
            session.setAttribute("tongTien", tongTien);
        }

        // Gửi tới layout chính
        req.getRequestDispatcher("/layouts/index.jsp").forward(req, resp);
    }
}
