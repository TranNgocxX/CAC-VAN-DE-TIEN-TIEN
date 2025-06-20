<%-- <%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sửa bình luận</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@400;600&display=swap" rel="stylesheet">
    <style>
        body {
            background-color: #fff0f5;
            font-family: 'Quicksand', sans-serif;
        }
        .btn-back {
            background-color: #f8bbd0;
            color: white;
            border: none;
        }
        .btn-back:hover {
            background-color: #f06292;
        }
    </style>
</head>
<body>
<%@ include file="/layouts/header.jsp" %>

<div class="container mt-5">
    <h2 class="text-primary mb-4">Sửa bình luận</h2>
    <form class="form-sua" action="${pageContext.request.contextPath}/binh-luan" method="post">
        <input type="hidden" name="idSanPham" value="${param.idSanPham}" />
        <input type="hidden" name="idBinhLuan" value="${param.id}" />
        <div class="input-group mb-3">
            <input type="text" name="noiDung" class="form-control" value="${binhLuan.noiDung}" required />
            <button type="submit" class="btn btn-outline-success">Lưu</button>
            <a href="${pageContext.request.contextPath}/xem-chi-tiet-san-pham?id=${param.idSanPham}" class="btn btn-outline-secondary">Hủy</a>
        </div>
    </form>
    <a href="${pageContext.request.contextPath}/xem-chi-tiet-san-pham?id=${param.idSanPham}" class="btn btn-back mt-4">← Quay lại chi tiết sản phẩm</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> --%>