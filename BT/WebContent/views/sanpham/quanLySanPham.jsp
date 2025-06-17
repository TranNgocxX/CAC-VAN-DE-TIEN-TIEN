<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="BTL.models.SanPham" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách sản phẩm</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .table-hover tbody tr:hover {
            background-color: #f5f5f5; /* Màu nền khi hover */
            transition: background-color 0.3s ease; /* Hiệu ứng chuyển màu */
        }

        .btn {
            transition: background-color 0.3s ease, transform 0.2s ease; /* Hiệu ứng chuyển màu và phóng to */
        }

        .btn:hover {
            background-color: #218838; /* Màu nền khi hover */
            transform: scale(1.05); /* Phóng to nút khi hover */
        }

        .fade-in {
            opacity: 0; /* Bắt đầu với độ mờ 0 */
            transition: opacity 0.5s ease; /* Hiệu ứng chuyển độ mờ */
        }

        .fade-in.show {
            opacity: 1; /* Đưa độ mờ về 1 khi có lớp show */
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Danh sách sản phẩm</h2>
        <a href="${pageContext.request.contextPath}/san-pham/them" class="btn btn-success">+ Thêm sản phẩm</a>
    </div>

    <table class="table table-bordered table-hover align-middle fade-in" id="productTable">
        <thead class="table-primary text-center">
            <tr>
                <th>ID</th>
                <th>Tên sản phẩm</th>
                <th>Mô tả</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th>Ảnh</th>
                <th>Ngày tạo</th>
                <th>Ngày cập nhật</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="sp" items="${danhSachSanPham}">
                <tr>
                    <td class="text-center">${sp.id}</td>
                    <td>${sp.tenSanPham}</td>
                    <td>${sp.moTa}</td>
                    <td class="text-end">
                        <fmt:formatNumber value="${sp.gia}" type="currency" currencySymbol="₫" />
                    </td>
                    <td class="text-center">${sp.soLuong}</td>
                    <td class="text-center">
                        <img src="${sp.anhUrl}" alt="ảnh sản phẩm" width="60" height="60" style="object-fit: cover;"/>
                    </td>
                    <td><fmt:formatDate value="${sp.ngayTao}" pattern="dd/MM/yyyy HH:mm" /></td>
                    <td><fmt:formatDate value="${sp.ngayCapNhat}" pattern="dd/MM/yyyy HH:mm" /></td>
                    <td class="text-center">
                        <a href="${pageContext.request.contextPath}/san-pham/sua?id=${sp.id}" class="btn btn-sm btn-warning">Sửa</a>
                        <a href="${pageContext.request.contextPath}/san-pham/xoa?id=${sp.id}" class="btn btn-sm btn-danger"
                           onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?');">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<script>
    window.onload = function() {
        const table = document.getElementById('productTable');
        table.classList.add('show'); // Thêm lớp show để hiển thị bảng
    };
</script>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>


