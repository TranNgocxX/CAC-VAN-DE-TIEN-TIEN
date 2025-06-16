package BTL.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import BTL.models.NguoiDung;
import BTL.patterns.QuanLyNguoiDungFacade;

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private QuanLyNguoiDungFacade quanLyNguoiDung;
    
    public AuthServlet() {
        super();
        this.quanLyNguoiDung = new QuanLyNguoiDungFacade();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }
        
        try {
            switch (action) {
                case "/dang-nhap":
                    hienTrangDangNhap(request, response);
                    break;
                case "/dang-ky":
                    hienTrangDangKy(request, response);
                    break;
                case "/dang-xuat":
                    dangXuat(request, response);
                    break;
                case "/thong-tin":
                    hienThongTinCaNhan(request, response);
                    break;
                case "/quan-ly-sp":
                    xuLyQuanLySanPham(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }
        
        try {
            switch (action) {
                case "/dang-nhap":
                    xuLyDangNhap(request, response);
                    break;
                case "/dang-ky":
                    xuLyDangKy(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
    
    private void hienTrangDangNhap(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/dangNhap.jsp").forward(request, response);
    }
    
    private void hienTrangDangKy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/dangKy.jsp").forward(request, response);
    }
    
    private void xuLyDangNhap(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tenDangNhap = request.getParameter("tenDangNhap");
        String matKhau = request.getParameter("matKhau");
        
        NguoiDung nguoiDung = quanLyNguoiDung.dangNhap(tenDangNhap, matKhau);
        
        if (nguoiDung != null) {
            HttpSession session = request.getSession();
            session.setAttribute("nguoiDung", nguoiDung);
            request.setAttribute("content", "/views/auth/thongTinCaNhan.jsp");
            request.setAttribute("title", "Thông tin cá nhân");
            request.setAttribute("active", "thong-tin");
            request.getRequestDispatcher("/layouts/header.jsp").forward(request, response);
        } else {
            request.setAttribute("loi", "Tên đăng nhập hoặc mật khẩu không đúng");
            request.getRequestDispatcher("/views/auth/dangNhap.jsp").forward(request, response);
        } 
    }
    
    private void xuLyDangKy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tenDangNhap = request.getParameter("tenDangNhap");
        String matKhau = request.getParameter("matKhau");
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String soDienThoai = request.getParameter("soDienThoai");
        
        NguoiDung nguoiDung = new NguoiDung(tenDangNhap, matKhau, hoTen, email, soDienThoai);
        
        if (quanLyNguoiDung.dangKy(nguoiDung)) {
            request.setAttribute("thanhCong", "Đăng ký thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("/views/auth/dangNhap.jsp").forward(request, response);
        } else {
            request.setAttribute("loi", "Tên đăng nhập đã tồn tại");
            request.getRequestDispatcher("/views/auth/dangKy.jsp").forward(request, response);
        }
    }
    
    private void dangXuat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/auth/dang-nhap");
    }
    
    private void hienThongTinCaNhan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("nguoiDung") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/dang-nhap");
            return;
        }
        
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        NguoiDung chiTiet = quanLyNguoiDung.layThongTinNguoiDung(nguoiDung.getId());
        
        request.setAttribute("nguoiDung", chiTiet);
        request.getRequestDispatcher("/views/auth/thongTinCaNhan.jsp").forward(request, response);
    }
    
//    private void xuLyQuanLySanPham(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("nguoiDung") == null) {
//            response.sendRedirect(request.getContextPath() + "/auth/dang-nhap");
//            return;
//        }
//        
//        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
//        if ("admin".equals(nguoiDung.getVaiTro())) {
//            response.sendRedirect(request.getContextPath() + "/san-pham");
//        } else {
//            request.setAttribute("error", "Bạn không có quyền truy cập chức năng này.");
//            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
//        }
//    }
    
    private void xuLyQuanLySanPham(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("nguoiDung") == null) {
        response.sendRedirect(request.getContextPath() + "/auth/dang-nhap");
        return;
    }

    // Bỏ kiểm tra vai trò, ai đăng nhập cũng được truy cập
    response.sendRedirect(request.getContextPath() + "/san-pham");
}
    
}