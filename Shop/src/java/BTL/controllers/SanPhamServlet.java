//package BTL.controllers;
//
//import BTL.daos.LoaiSanPhamDAO;
//import BTL.models.LoaiSanPham;
//import BTL.models.SanPham;
//import BTL.patterns.SanPhamDAOProxy;
//import BTL.patterns.SanPhamFactory;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//import javax.servlet.http.HttpSession;
//
//@WebServlet(name = "SanPhamServlet", urlPatterns = {"/san-pham/*"})
//public class SanPhamServlet extends HttpServlet {
//    private SanPhamDAOProxy sanPhamDAOProxy;
//    private LoaiSanPhamDAO loaiSanPhamDAO;
//
//    @Override
//    public void init() throws ServletException {
//        sanPhamDAOProxy = new SanPhamDAOProxy();
//        loaiSanPhamDAO = new LoaiSanPhamDAO();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//    if (session == null || session.getAttribute("nguoiDung") == null) {
//        response.sendRedirect(request.getContextPath() + "/auth/dang-nhap");
//        return;
//    }
//    String path = request.getPathInfo();
//        try {
//            if (path == null || path.equals("/")) {
//                List<SanPham> sanPhams = sanPhamDAOProxy.getAllSanPham(request);
//                request.setAttribute("sanPhams", sanPhams);
//                request.getRequestDispatcher("/views/sanpham/quanLySanPham.jsp").forward(request, response);
//            } else if (path.equals("/them")) {
//                List<LoaiSanPham> loaiSanPhams = loaiSanPhamDAO.getAllLoaiSanPham();
//                request.setAttribute("loaiSanPhams", loaiSanPhams);
//                request.getRequestDispatcher("/views/sanpham/themSanPham.jsp").forward(request, response);
//            } else if (path.equals("/sua")) {
//                int id = Integer.parseInt(request.getParameter("id"));
//                SanPham sanPham = sanPhamDAOProxy.getSanPhamById(request, id);
//                List<LoaiSanPham> loaiSanPhams = loaiSanPhamDAO.getAllLoaiSanPham();
//                request.setAttribute("sanPham", sanPham);
//                request.setAttribute("loaiSanPhams", loaiSanPhams);
//                request.getRequestDispatcher("/views/sanpham/suaSanPham.jsp").forward(request, response);
//            } else if (path.equals("/chi-tiet")) {
//                int id = Integer.parseInt(request.getParameter("id"));
//                SanPham sanPham = sanPhamDAOProxy.getSanPhamById(request, id);
//                request.setAttribute("sanPham", sanPham);
//                request.getRequestDispatcher("/views/sanpham/chiTietSanPham.jsp").forward(request, response);
//            } else if (path.equals("/xoa")) {
//                int id = Integer.parseInt(request.getParameter("id"));
//                sanPhamDAOProxy.deleteSanPham(request, id);
//                response.sendRedirect(request.getContextPath() + "/san-pham");
//            }
//        } catch (SQLException e) {
//            throw new ServletException(e);
//        } catch (SecurityException e) {
//            request.setAttribute("error", e.getMessage());
//            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String path = request.getPathInfo();
//        try {
//            if (path.equals("/them")) {
//                SanPham sanPham = SanPhamFactory.createSanPham(
//                        request.getParameter("tenSanPham"),
//                        request.getParameter("moTa"),
//                        Double.parseDouble(request.getParameter("gia")),
//                        Integer.parseInt(request.getParameter("soLuong")),
//                        request.getParameter("anhUrl"),
//                        Integer.parseInt(request.getParameter("idLoai"))
//                );
//                sanPhamDAOProxy.addSanPham(request, sanPham);
//                response.sendRedirect(request.getContextPath() + "/san-pham");
//            } else if (path.equals("/sua")) {
//                SanPham sanPham = SanPhamFactory.createSanPhamWithId(
//                        Integer.parseInt(request.getParameter("id")),
//                        request.getParameter("tenSanPham"),
//                        request.getParameter("moTa"),
//                        Double.parseDouble(request.getParameter("gia")),
//                        Integer.parseInt(request.getParameter("soLuong")),
//                        request.getParameter("anhUrl"),
//                        Integer.parseInt(request.getParameter("idLoai"))
//                );
//                sanPhamDAOProxy.updateSanPham(request, sanPham);
//                response.sendRedirect(request.getContextPath() + "/san-pham");
//            }
//        } catch (SQLException e) {
//            throw new ServletException(e);
//        } catch (SecurityException e) {
//            request.setAttribute("error", e.getMessage());
//            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
//        }
//    }
//}

package BTL.controllers;

import BTL.models.NguoiDung;
import BTL.models.SanPham;
import BTL.patterns.SanPhamProxy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/san-pham/*")
public class SanPhamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("nguoiDung") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/dang-nhap");
            return;
        }

        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        SanPhamProxy sanPhamProxy = new SanPhamProxy(nguoiDung);
        String action = request.getPathInfo() != null ? request.getPathInfo() : "/";

        try {
            switch (action) {
                case "/":
                    hienDanhSachSanPham(request, response, sanPhamProxy);
                    break;
                case "/them":
                    hienFormThemSanPham(request, response, sanPhamProxy);
                    break;
                case "/sua":
                    hienFormSuaSanPham(request, response, sanPhamProxy);
                    break;
                case "/chi-tiet":
                    hienChiTietSanPham(request, response, sanPhamProxy);
                    break;
                case "/xoa":
                    xoaSanPham(request, response, sanPhamProxy);
                    break;
                default:
                    request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
                    break;
            }
        } catch (SecurityException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/errors/403.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("nguoiDung") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/dang-nhap");
            return;
        }

        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        SanPhamProxy sanPhamProxy = new SanPhamProxy(nguoiDung);
        String action = request.getPathInfo() != null ? request.getPathInfo() : "/";

        try {
            switch (action) {
                case "/them":
                    xuLyThemSanPham(request, response, sanPhamProxy);
                    break;
                case "/sua":
                    xuLySuaSanPham(request, response, sanPhamProxy);
                    break;
                default:
                    request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
                    break;
            }
        } catch (SecurityException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/errors/403.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    private void hienDanhSachSanPham(HttpServletRequest request, HttpServletResponse response, SanPhamProxy sanPhamProxy) throws ServletException, IOException {
        request.setAttribute("dsSanPham", sanPhamProxy.layTatCa());
        request.getRequestDispatcher("/views/sanpham/quanLySanPham.jsp").forward(request, response);
    }

    private void hienFormThemSanPham(HttpServletRequest request, HttpServletResponse response, SanPhamProxy sanPhamProxy) throws ServletException, IOException {
        request.setAttribute("dsLoaiSanPham", sanPhamProxy.layTatCaLoaiSanPham());
        request.getRequestDispatcher("/views/sanpham/themSanPham.jsp").forward(request, response);
    }

    private void hienFormSuaSanPham(HttpServletRequest request, HttpServletResponse response, SanPhamProxy sanPhamProxy) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("sanPham", sanPhamProxy.timTheoId(id));
        request.setAttribute("dsLoaiSanPham", sanPhamProxy.layTatCaLoaiSanPham());
        request.getRequestDispatcher("/views/sanpham/suaSanPham.jsp").forward(request, response);
    }

    private void hienChiTietSanPham(HttpServletRequest request, HttpServletResponse response, SanPhamProxy sanPhamProxy) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("sanPham", sanPhamProxy.timTheoId(id));
        request.getRequestDispatcher("/views/sanpham/chiTietSanPham.jsp").forward(request, response);
    }

    private void xoaSanPham(HttpServletRequest request, HttpServletResponse response, SanPhamProxy sanPhamProxy) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        sanPhamProxy.xoaSanPham(id);
        response.sendRedirect(request.getContextPath() + "/san-pham");
    }

    private void xuLyThemSanPham(HttpServletRequest request, HttpServletResponse response, SanPhamProxy sanPhamProxy) throws ServletException, IOException {
        SanPham sanPham = new SanPham.SanPhamBuilder()
                .setTenSanPham(request.getParameter("tenSanPham"))
                .setMoTa(request.getParameter("moTa"))
                .setGia(Double.parseDouble(request.getParameter("gia")))
                .setSoLuong(Integer.parseInt(request.getParameter("soLuong")))
                .setAnhUrl(request.getParameter("anhUrl"))
                .setIdLoai(Integer.parseInt(request.getParameter("idLoai")))
                .setNgayTao(new Timestamp(System.currentTimeMillis()))
                .setNgayCapNhat(new Timestamp(System.currentTimeMillis()))
                .build();

        if (sanPhamProxy.themSanPham(sanPham)) {
            response.sendRedirect(request.getContextPath() + "/san-pham");
        } else {
            request.setAttribute("error", "Thêm sản phẩm thất bại!");
            request.getRequestDispatcher("/views/sanpham/themSanPham.jsp").forward(request, response);
        }
    }

    private void xuLySuaSanPham(HttpServletRequest request, HttpServletResponse response, SanPhamProxy sanPhamProxy) throws ServletException, IOException {
        SanPham sanPham = new SanPham.SanPhamBuilder()
                .setId(Integer.parseInt(request.getParameter("id")))
                .setTenSanPham(request.getParameter("tenSanPham"))
                .setMoTa(request.getParameter("moTa"))
                .setGia(Double.parseDouble(request.getParameter("gia")))
                .setSoLuong(Integer.parseInt(request.getParameter("soLuong")))
                .setAnhUrl(request.getParameter("anhUrl"))
                .setIdLoai(Integer.parseInt(request.getParameter("idLoai")))
                .setNgayCapNhat(new Timestamp(System.currentTimeMillis()))
                .build();

        if (sanPhamProxy.suaSanPham(sanPham)) {
            response.sendRedirect(request.getContextPath() + "/san-pham");
        } else {
            request.setAttribute("error", "Sửa sản phẩm thất bại!");
            request.getRequestDispatcher("/views/sanpham/suaSanPham.jsp").forward(request, response);
        }
    }
}