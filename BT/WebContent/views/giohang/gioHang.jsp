<%-- <%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="BTL.models.GioHang" %>
<%@ page import="BTL.models.SanPham" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Giỏ hàng của bạn</h2>
        <table class="table">
            <thead>
                <tr>
                    <th>Tên sản phẩm</th>
                    <th>Số lượng</th>
                    <th>Giá</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    GioHang gioHang = (GioHang) request.getAttribute("gioHang");
                    if (gioHang != null && gioHang.getSanPhamList() != null) {
                        for (SanPham sp : gioHang.getSanPhamList()) { 
                %>
                <tr>
                    <td><%= sp.getTenSanPham() %></td>
                    <td><%= sp.getSoLuong() %></td>
                    <td>₫<%= String.format("%,.0f", sp.getGia()) %></td>
                    <td>
                        <form action="<%= request.getContextPath() %>/gio-hang" method="post">
                            <input type="hidden" name="idGioHang" value="<%= gioHang.getId() %>">
                            <input type="hidden" name="idSanPham" value="<%= sp.getId() %>">
                            <input type="number" name="soLuong" value="<%= sp.getSoLuong() %>" min="1">
                            <button type="submit" name="action" value="update" class="btn btn-warning">Cập nhật</button>
                            <button type="submit" name="action" value="remove" class="btn btn-danger">Xóa</button>
                        </form>
                    </td>
                </tr>
                <% 
                        } 
                    } else { 
                %>
                <tr>
                    <td colspan="4" class="text-center">Giỏ hàng trống</td>
                </tr>
                <% 
                    } 
                %>
            </tbody>
        </table>
        <a href="<%= request.getContextPath() %>/thanh-toan" class="btn btn-success">Thanh toán</a>
    </div>
</body>
</html> --%>




