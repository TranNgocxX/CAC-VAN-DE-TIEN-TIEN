package BTL.controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import BTL.models.BinhLuan;
import BTL.models.NguoiDung;
import BTL.patterns.QuanLyBinhLuanFacade;

@WebServlet("/binh-luan")
public class BinhLuanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private QuanLyBinhLuanFacade blFacade = new QuanLyBinhLuanFacade();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        int idSanPham = Integer.parseInt(request.getParameter("idSanPham"));
        String noiDung = request.getParameter("noiDung");

        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("nguoiDung");

        BinhLuan bl = new BinhLuan();
        bl.setIdNguoiDung(nd.getId()); 
        bl.setIdSanPham(idSanPham);
        bl.setNoiDung(noiDung);
        bl.setNgayTao(new Timestamp(System.currentTimeMillis()));
        bl.setNgayCapNhat(bl.getNgayTao());

        blFacade.themBinhLuan(bl);

        response.sendRedirect(request.getContextPath() + "/san-pham/chi-tiet?id=" + idSanPham);
    }
}
