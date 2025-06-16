<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa sản phẩm | MyPhamShop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/views/layouts/header.jsp" %>
<div class="container mt-4">
    <h2>Sửa sản phẩm</h2>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/san-pham/sua" method="post">
        <input type="hidden" name="id" value="${sanPham.id}">
        <div class="mb-3">
            <label for="tenSanPham" class="form-label">Tên sản phẩm</label>
            <input type="text" class="form-control" id="tenSanPham" name="tenSanPham" value="${sanPham.tenSanPham}" required>
        </div>
        <div class="mb-3">
            <label for="moTa" class="form-label">Mô tả</label>
            <textarea class="form-control" id="moTa" name="moTa" rows="4">${sanPham.moTa}</textarea>
        </div>
        <div class="mb-3">
            <label for="gia" class="form-label">Giá</label>
            <input type="number" class="form-control" id="gia" name="gia" step="0.01" value="${sanPham.gia}" required>
        </div>
        <div class="mb-3">
            <label for="soLuong" class="form-label">Số lượng</label>
            <input type="number" class="form-control" id="soLuong" name="soLuong" value="${sanPham.soLuong}" required>
        </div>
        <div class="mb-3">
            <label for="anhUrl" class="form-label">URL ảnh</label>
            <input type="text" class="form-control" id="anhUrl" name="anhUrl" value="${sanPham.anhUrl}">
        </div>
        <div class="mb-3">
            <label for="idLoai" class="form-label">Loại sản phẩm</label>
            <select class="form-select" id="idLoai" name="idLoai" required>
                <c:forEach var="loai" items="${dsLoaiSanPham}">
                    <option value="${loai.id}" ${loai.id == sanPham.idLoai ? 'selected' : ''}>${loai.tenLoai}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
        <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-secondary">Hủy</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>