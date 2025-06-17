<%@ page import="BTL.models.NguoiDung" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${param.title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .navbar-nav .nav-link.active {
            font-weight: bold;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/san-pham">BTL Shop</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link ${param.active eq 'san-pham' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/san-pham">Sản phẩm</a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <% if (session.getAttribute("nguoiDung") == null) { %>
                        <li class="nav-item">
                            <a class="nav-link ${param.active eq 'dang-nhap' ? 'active' : ''}" 
                               href="${pageContext.request.contextPath}/auth/dang-nhap">Đăng nhập</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${param.active eq 'dang-ky' ? 'active' : ''}" 
                               href="${pageContext.request.contextPath}/auth/dang-ky">Đăng ký</a>
                        </li>
                    <% } else { 
                        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
                    %>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" 
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Xin chào, <%= nguoiDung.getHoTen() %>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" 
                                       href="${pageContext.request.contextPath}/auth/thong-tin">Thông tin cá nhân</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" 
                                       href="${pageContext.request.contextPath}/auth/dang-xuat">Đăng xuất</a></li>
                            </ul>
                        </li>
                    <% } %>
                </ul>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <jsp:include page="${param.content}" />
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>