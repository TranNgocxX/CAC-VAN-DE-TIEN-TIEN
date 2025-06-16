<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý sản phẩm | MyPhamShop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: "Helvetica Neue", sans-serif;
            background-color: #f8f9fa;
        }
        .table img {
            width: 50px;
            height: 50px;
            object-fit: cover;
        }
    </style>
</head>
<body>
<%@ include file="/views/layouts/header.jsp" %>
<div class="container mt-4">
    <h2>Quản lý sản phẩm</h2>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <a href="${pageContext.request.contextPath}/san-pham/them" class="btn btn-primary mb-3">Thêm sản phẩm</a>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>Tên sản phẩm</th>
                <th>Mô tả</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th>Ảnh</th>
                <th>Loại</th>
                <th>Ngày tạo</th>
                <th>Ngày cập nhật</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="sanPham" items="${dsSanPham}">
                <tr>
                    <td>${sanPham.id}</td>
                    <td>${sanPham.tenSanPham}</td>
                    <td>${sanPham.moTa}</td>
                    <td><fmt:formatNumber value="${sanPham.gia}" type="currency" currencySymbol="₫"/></td>
                    <td>${sanPham.soLuong}</td>
                    <td><img src="${sanPham.anhUrl}" alt="${sanPham.tenSanPham}"></td>
                    <td>${sanPham.idLoai}</td>
                    <td><fmt:formatDate value="${sanPham.ngayTao}" pattern="dd/MM/yyyy HH:mm"/></td>
                    <td><fmt:formatDate value="${sanPham.ngayCapNhat}" pattern="dd/MM/yyyy HH:mm"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/san-pham/chi-tiet?id=${sanPham.id}" class="btn btn-info btn-sm">Chi tiết</a>
                        <a href="${pageContext.request.contextPath}/san-pham/sua?id=${sanPham.id}" class="btn btn-warning btn-sm">Sửa</a>
                        <a href="${pageContext.request.contextPath}/san-pham/xoa?id=${sanPham.id}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>