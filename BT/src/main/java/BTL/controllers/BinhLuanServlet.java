package BTL.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import BTL.models.BinhLuan;
import BTL.models.NguoiDung;
import BTL.patterns.QuanLyBinhLuanFacade;

@WebServlet("/binh-luan/*")
public class BinhLuanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private QuanLyBinhLuanFacade quanLyBinhLuan;
    
    public BinhLuanServlet() {
        super();
        this.quanLyBinhLuan = new QuanLyBinhLuanFacade();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        
        try {
            switch (action) {
                case "/them":
                    hienTrangThemBinhLuan(request, response);
                    break;
                case "/xem":
                    hienTrangXemBinhLuan(request, response);
                    break;
                default:
                    response.sendRedirect("../san-pham");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        
        try {
            switch (action) {
                case "/them":
                    xuLyThemBinhLuan(request, response);
                    break;
                default:
                    response.sendRedirect("../san-pham");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    private void hienTrangThemBinhLuan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idSanPham = Integer.parseInt(request.getParameter("idSanPham"));
        request.setAttribute("idSanPham", idSanPham);
        request.getRequestDispatcher("/views/binhLuan/themBinhLuan.jsp").forward(request, response);
    }
    
    private void hienTrangXemBinhLuan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idSanPham = Integer.parseInt(request.getParameter("idSanPham"));
        var dsBinhLuan = quanLyBinhLuan.layBinhLuanTheoSanPham(idSanPham);
        request.setAttribute("dsBinhLuan", dsBinhLuan);
        request.getRequestDispatcher("/views/binhLuan/xemBinhLuan.jsp").forward(request, response);
    }
    
    private void xuLyThemBinhLuan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("nguoiDung") == null) {
            response.sendRedirect("../auth/dang-nhap");
            return;
        }
        
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        int idSanPham = Integer.parseInt(request.getParameter("idSanPham"));
        String noiDung = request.getParameter("noiDung");
        
        BinhLuan binhLuan = new BinhLuan(nguoiDung.getId(), idSanPham, noiDung);
        
        if (quanLyBinhLuan.themBinhLuan(binhLuan)) {
            response.sendRedirect("../binh-luan/xem?idSanPham=" + idSanPham);
        } else {
            request.setAttribute("loi", "Thêm bình luận thất bại");
            request.getRequestDispatcher("/views/binhLuan/themBinhLuan.jsp").forward(request, response);
        }
    }
}