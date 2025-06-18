<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="BTL.models.NguoiDung" %>
<%@ page import="BTL.models.LoaiSanPham" %>
<%@ page import="BTL.daos.LoaiSanPhamDAO" %>
<%@ page import="java.util.List" %>

<%
    LoaiSanPhamDAO loaiDAO = new LoaiSanPhamDAO();
    List<LoaiSanPham> danhSachLoai = loaiDAO.layTatCa();
    NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
%>

<nav class="navbar navbar-expand-lg" style="background-color: #f8bbd0;">
    <div class="container-fluid px-4">
        <a class="navbar-brand text-white fw-bold" href="<%= request.getContextPath() %>/trang-chu">Girlss</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-between" id="navbarHeader">
            <!-- Danh sách loại ở giữa -->
            <ul class="navbar-nav mx-auto">
                <% for (LoaiSanPham loai : danhSachLoai) { %>
                    <li class="nav-item">
                        <a class="nav-link text-white px-3" href="<%= request.getContextPath() %>/trang-chu?idLoai=<%= loai.getId() %>">
                            <%= loai.getTenLoai() %>
                        </a>
                    </li>
                <% } %>
            </ul>

            <ul class="navbar-nav">
                <% if (nguoiDung == null) { %>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="<%= request.getContextPath() %>/auth/dang-nhap">Đăng nhập</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="<%= request.getContextPath() %>/auth/dang-ky">Đăng ký</a>
                    </li>
                <% } else { %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown">
                            Xin chào, <%= nguoiDung.getHoTen() %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="<%= request.getContextPath() %>/auth/thong-tin">Thông tin cá nhân</a></li>
                            <li><a class="dropdown-item" href="<%= request.getContextPath() %>/san-pham/quan-ly">Quản lý sản phẩm</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="<%= request.getContextPath() %>/auth/dang-xuat">Đăng xuất</a></li>
                        </ul>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>
