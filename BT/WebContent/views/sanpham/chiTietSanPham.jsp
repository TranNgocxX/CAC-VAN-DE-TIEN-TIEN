<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="BTL.models.SanPham" %>
<%
    SanPham sp = (SanPham) request.getAttribute("sp");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title><%= sp.getTenSanPham() %> - Chi tiết sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@400;600&display=swap" rel="stylesheet">
    <style>
        body {
            background-color: #fff0f5;
            font-family: 'Quicksand', sans-serif;
        }
        .product-image {
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .product-title {
            color: #d81b60;
            font-weight: 600;
        }
        .product-price {
            color: #c2185b;
            font-size: 1.6rem;
            font-weight: bold;
        }
        .product-detail {
            background-color: #fce4ec;
            border-radius: 8px;
            padding: 1rem;
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
    <div class="row align-items-center">
        <div class="col-md-6 mb-4">
            <img src="<%= request.getContextPath() %>/images/<%= sp.getAnhUrl() %>" class="img-fluid product-image" alt="<%= sp.getTenSanPham() %>">
        </div>
        <div class="col-md-6">
            <h2 class="product-title"><%= sp.getTenSanPham() %></h2>
            <p class="product-price">₫<%= String.format("%,.0f", sp.getGia()) %></p>
            <p><strong>Số lượng còn:</strong> <%= sp.getSoLuong() %></p>
            <p><strong>Mô tả chi tiết:</strong></p>
            <div class="product-detail mb-4">
                <%= sp.getMoTa() %>
            </div>

            <p><strong>Bình luận:</strong></p>
            <div class="mt-3 mb-4">
                <c:forEach var="bl" items="${danhSachBinhLuan}">
                    <div class="border rounded p-3 mb-2 bg-white shadow-sm">
                        <p class="mb-1 fw-bold text-primary">${bl.nguoiDung.hoTen}
                            <span class="text-muted small">(${bl.ngayTao})</span>
                        </p>
                        <p class="mb-0">${bl.noiDung}</p>
                    </div>
                </c:forEach>

                <c:if test="${empty danhSachBinhLuan}">
                    <p class="text-muted">Chưa có bình luận nào.</p>
                </c:if>
            </div>

            <c:if test="${not empty sessionScope.nguoiDung}">
                <form action="${pageContext.request.contextPath}/binh-luan" method="post">
                    <input type="hidden" name="idSanPham" value="<%= sp.getId() %>" />
                    <div class="mb-3">
                        <textarea name="noiDung" class="form-control" rows="2" placeholder="Nhập bình luận..." required></textarea>
                    </div>
                    <button type="submit" class="btn btn-outline-danger">Gửi bình luận</button>
                </form>
            </c:if>

            <c:if test="${empty sessionScope.nguoiDung}">
                <p class="text-muted fst-italic">
                    Vui lòng <a href="${pageContext.request.contextPath}/auth/dang-nhap">đăng nhập</a> để bình luận.
                </p>
            </c:if>

                <a href="<%= request.getContextPath() %>/trang-chu" class="btn btn-back mt-4">← Quay lại trang chủ</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
