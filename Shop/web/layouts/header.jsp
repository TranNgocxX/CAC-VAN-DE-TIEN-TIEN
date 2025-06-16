<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="BTL.models.NguoiDung" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<%
    NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    String tenNguoiDung = (nguoiDung != null) ? nguoiDung.getHoTen() : null;
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chủ | MyPhamShop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: "Helvetica Neue", sans-serif;
            background-color: #f8f9fa;
        }
        .hero {
            background-color: #fff;
            padding: 60px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            text-align: center;
            margin-bottom: 40px;
        }
        .footer {
            background-color: #212529;
            color: #fff;
            padding: 30px 0;
            text-align: center;
            margin-top: 50px;
        }
        .navbar-brand {
            font-weight: bold;
        }
        img {
            width: 60px;
            height: 60px;
            object-fit: contain;
        }
        .navbar-nav .nav-link.active {
            font-weight: bold;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary px-4">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/san-pham">MyPhamShop</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">

            </ul>
            <ul class="navbar-nav">
                <% if (nguoiDung == null) { %>
                    <li class="nav-item">
                        <a class="nav-link ${active eq 'dang-nhap' ? 'active' : ''}" href="${pageContext.request.contextPath}/auth/dang-nhap">Đăng nhập</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${active eq 'dang-ky' ? 'active' : ''}" href="${pageContext.request.contextPath}/auth/dang-ky">Đăng ký</a>
                    </li>
                <% } else { %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Xin chào, <%= nguoiDung.getHoTen() %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/thong-tin">Thông tin cá nhân</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/san-pham">Quản lý sản phẩm</a>                            
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/dang-xuat">Đăng xuất</a></li>
                        </ul>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>

<!-- Nội dung chính -->
<div class="container mt-4">
    <!-- Hero Banner -->
    <div class="hero">
        <h2 class="fw-bold">Bộ sưu tập mỹ phẩm cao cấp</h2>
        <p class="text-muted">Giảm giá lên đến 40% cho tất cả sản phẩm!</p>
        <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-light btn-lg">Mua ngay</a>
    </div>

    <!-- Danh mục nổi bật -->
    <h4 class="text-center mb-3">Danh mục nổi bật</h4>
    <div class="row text-center mb-4">
        <div class="col-md-3"><img src="img/srm.png" alt="Sữa rửa mặt"><p>Sữa rửa mặt</p></div>
        <div class="col-md-3"><img src="img/kcn.png" alt="Kem chống nắng"><p>Kem chống nắng</p></div>
        <div class="col-md-3"><img src="img/serum.png" alt="Serum"><p>Serum</p></div>
        <div class="col-md-3"><img src="img/duongam.png" alt="Dưỡng ẩm"><p>Dưỡng ẩm</p></div>
    </div>

    <!-- Sản phẩm bán chạy -->
    <h4 class="text-center mb-3">Sản phẩm bán chạy</h4>
    <div class="row text-center">
        <div class="col-md-3"><img src="img/sp1.png" alt="Sản phẩm 1"><p>Sản phẩm 1 - 350.000đ</p></div>
        <div class="col-md-3"><img src="img/sp2.png" alt="Sản phẩm 2"><p>Sản phẩm 2 - 450.000đ</p></div>
        <div class="col-md-3"><img src="img/sp3.png" alt="Sản phẩm 3"><p>Sản phẩm 3 - 550.000đ</p></div>
        <div class="col-md-3"><img src="img/sp4.png" alt="Sản phẩm 4"><p>Sản phẩm 4 - 650.000đ</p></div>
    </div>
</div>

<!-- Footer -->
<div class="footer">
    <p>&copy; 2025 MyPhamShop. All rights reserved.</p>
    <p>Email: lienhe@myphamshop.vn | Hotline: 1900 1234</p>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
