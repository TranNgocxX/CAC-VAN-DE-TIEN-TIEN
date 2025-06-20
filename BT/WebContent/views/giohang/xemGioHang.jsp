<%-- <%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="BTL.models.GioHangChiTiet" %>
<%@ page import="java.util.List" %>
<%
    List<GioHangChiTiet> danhSachSanPham = (List<GioHangChiTiet>) request.getAttribute("danhSachSanPham");
%>
<html>
<head>
    <title>Giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container py-5">
<h2 class="mb-4">Giỏ hàng của bạn</h2>
<form action="${pageContext.request.contextPath}/gio-hang" method="post">
    <table class="table">
        <thead>
        <tr>
            <th>Sản phẩm</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Thành tiền</th>
            <th>Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ct" items="${danhSachSanPham}">
            <tr>
                <td>${ct.sanPham.tenSanPham}</td>
                <td>₫<fmt:formatNumber value="${ct.sanPham.gia}" pattern="#.###"/></td>
                <td><input type="number" name="soLuong" value="${ct.soLuong}" min="0" class="form-control"/></td>
                <td>₫<fmt:formatNumber value="${ct.soLuong * ct.sanPham.gia}" pattern="#.###"/></td>
                <td>
                    <input type="hidden" name="idSanPham" value="${ct.idSanPham}"/>
                    <button type="submit" class="btn btn-sm btn-primary">Cập nhật</button>
                    <button type="submit" name="hanhDong" value="xoa" class="btn btn-sm btn-danger">Xoá</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</form>
</body>
</html>
 --%>