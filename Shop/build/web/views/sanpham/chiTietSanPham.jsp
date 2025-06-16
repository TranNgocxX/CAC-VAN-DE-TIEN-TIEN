<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sản phẩm | MyPhamShop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .product-img {
            max-width: 300px;
            height: auto;
        }
    </style>
</head>
<body>
<%@ include file="/views/layouts/header.jsp" %>
<div class="container mt-4">
    <h2>Chi tiết sản phẩm</h2>
    <div class="row">
        <div class="col-md-6">
            <img src="${sanPham.anhUrl}" alt="${sanPham.tenSanPham}" class="product-img">
        </div>
        <div class="col-md-6">
            <h3>${sanPham.tenSanPham}</h3>
            <p><strong>Mô tả:</strong> ${sanPham.moTa}</p>
            <p><strong>Giá:</strong> <fmt:formatNumber value="${sanPham.gia}" type="currency" currencySymbol="₫"/></p>
            <p><strong>Số lượng:</strong> ${sanPham.soLuong}</p>
            <p><strong>Loại sản phẩm:</strong> ${sanPham.idLoai}</p>
            <p><strong>Ngày tạo:</strong> <fmt:formatDate value="${sanPham.ngayTao}" pattern="dd/MM/yyyy HH:mm"/></p>
            <p><strong>Ngày cập nhật:</strong> <fmt:formatDate value="${sanPham.ngayCapNhat}" pattern="dd/MM/yyyy HH:mm"/></p>
            <a href="${pageContext.request.contextPath}/san-pham/sua?id=${sanPham.id}" class="btn btn-warning">Sửa</a>
            <a href="${pageContext.request.contextPath}/san-pham/xoa?id=${sanPham.id}" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
            <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-secondary">Quay lại</a>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>