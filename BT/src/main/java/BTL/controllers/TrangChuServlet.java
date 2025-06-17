package BTL.controllers;

import BTL.daos.SanPhamDAO;
import BTL.daos.LoaiSanPhamDAO;
import BTL.models.SanPham;
import BTL.models.LoaiSanPham;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/trang-chu")
public class TrangChuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idLoaiStr = req.getParameter("idLoai");
        int idLoai = 0;
        try {
            idLoai = Integer.parseInt(idLoaiStr);
        } catch (Exception ignored) {}

        SanPhamDAO spDAO = new SanPhamDAO();
        LoaiSanPhamDAO loaiDAO = new LoaiSanPhamDAO();

        List<SanPham> dsSanPham = idLoai > 0 ? spDAO.layTheoLoai(idLoai) : spDAO.layTatCa();
        List<LoaiSanPham> dsLoai = loaiDAO.layTatCa();

        req.setAttribute("danhSachSanPham", dsSanPham);
        req.setAttribute("danhSachLoai", dsLoai);
        req.setAttribute("content", "/views/trangChu.jsp"); // để include trong header.jsp

        req.getRequestDispatcher("/layouts/header.jsp").forward(req, resp);
    }
}
