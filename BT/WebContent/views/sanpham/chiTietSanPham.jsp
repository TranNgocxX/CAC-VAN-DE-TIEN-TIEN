<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="BTL.models.SanPham" %>
<%
    SanPham sp = (SanPham) request.getAttribute("sp");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= sp.getTenSanPham() %> - Chi tiết sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-6">
                <img src="<%= sp.getAnhUrl() %>" class="img-fluid" alt="<%= sp.getTenSanPham() %>">
            </div>
            <div class="col-md-6">
                <h2><%= sp.getTenSanPham() %></h2>
                <p class="text-danger fs-4 fw-bold">₫<%= String.format("%,.0f", sp.getGia()) %></p>
                <p><strong>Số lượng:</strong> <%= sp.getSoLuong() %></p>
                <p><strong>Mô tả:</strong> <%= sp.getMoTa() %></p>
                <a href="<%= request.getContextPath() %>/trang-chu" class="btn btn-secondary">Quay lại</a>
            </div>
        </div>
    </div>
</body>
</html>
