<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý giỏ hàng</title>
<style>
    body {
        font-family: 'Segoe UI', sans-serif;
        background: #fdf6f9;
        color: #333;
        margin: 20px;
    }

    h2 {
        color: #c94c94;
        text-align: center;
        margin-bottom: 30px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        background-color: #fff;
        box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        border-radius: 8px;
        overflow: hidden;
    }

    th, td {
        padding: 12px 16px;
        border-bottom: 1px solid #eee;
        text-align: left;
    }

    th {
        background-color: #fbe8f1;
        color: #c94c94;
        font-weight: bold;
        font-size: 15px;
    }

    tr:hover {
        background-color: #fff5fa;
    }

    .action-buttons button {
        background-color: #d86b9a;
        color: white;
        border: none;
        padding: 6px 12px;
        margin: 2px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
    }

    .action-buttons button:hover {
        background-color: #c0528a;
    }

    .edit-form {
        background-color: #fff0f6;
        border: 1px solid #e3a6c6;
        padding: 20px;
        border-radius: 8px;
        margin-top: 30px;
        max-width: 500px;
        margin-left: auto;
        margin-right: auto;
        display: none;
    }

    .edit-form.active {
        display: block;
    }

    .edit-form h3 {
        color: #c94c94;
        margin-bottom: 15px;
    }

    .edit-form label {
        display: block;
        margin-top: 12px;
        color: #555;
    }

    .edit-form input[type="text"],
    .edit-form input[type="number"],
    .edit-form select {
        width: 100%;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 6px;
        margin-top: 4px;
    }

    .edit-form button {
        margin-top: 15px;
        padding: 8px 16px;
        background-color: #c94c94;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-weight: bold;
    }

    .edit-form button:hover {
        background-color: #af3c7b;
    }

    .error {
        color: #d8000c;
        background-color: #ffbaba;
        padding: 10px;
        border-radius: 5px;
        margin-bottom: 10px;
    }
</style>

    <script>
        function showEditForm(idChiTiet, tenNguoiDung, tenSanPham, soLuong, trangThai) {
            document.getElementById('editIdChiTiet').value = idChiTiet;
            document.getElementById('editTenNguoiDung').value = tenNguoiDung;
            document.getElementById('editTenSanPham').value = tenSanPham;
            document.getElementById('editSoLuong').value = soLuong;
            // Ánh xạ trạng thái
            let normalizedTrangThai = trangThai === 'Đang xử lý' ? 'dang_mua' :
                                     trangThai === 'Đã đặt hàng' ? 'da_dat_hang' :
                                     trangThai === 'Hủy' ? 'da_huy' : trangThai;
            document.getElementById('editTrangThai').value = normalizedTrangThai;
            document.getElementById('editForm').classList.add('active');
        }
    </script>
</head>
<body>
    <h2>Quản lý giỏ hàng</h2>
    
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <table>
        <tr>
            <th>ID Chi tiết</th>
            <th>Tên người dùng</th>
            <th>Tên sản phẩm</th>
            <th>Loại sản phẩm</th>
            <th>Số lượng</th>
            <th>Giá</th>
            <th>Tổng tiền</th>
            <th>Ngày thêm</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        <c:forEach var="chiTiet" items="${danhSachChiTiet}">
            <tr>
                <td>${chiTiet.idChiTiet}</td>
                <td>${chiTiet.tenNguoiDung}</td>
                <td>${chiTiet.tenSanPham}</td>
                <td>${chiTiet.tenLoai}</td>
                <td>${chiTiet.soLuong}</td>
                <td>${chiTiet.gia}</td>
                <td>${chiTiet.tongTien}</td>
                <td>${chiTiet.ngayThem}</td>
                <td>${chiTiet.trangThai}</td>
                <td class="action-buttons">
                    <button onclick="showEditForm(${chiTiet.idChiTiet}, '${chiTiet.tenNguoiDung}', '${chiTiet.tenSanPham}', ${chiTiet.soLuong}, '${chiTiet.trangThai}')">Sửa</button>
                    <form action="${pageContext.request.contextPath}/quan-ly-gio-hang" method="post">
                        <input type="hidden" name="action" value="xoa-chi-tiet">
                        <input type="hidden" name="idGioHangChiTiet" value="${chiTiet.idChiTiet}">
                        <button type="submit">Xóa</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <div id="editForm" class="edit-form">
        <h3>Chỉnh sửa chi tiết giỏ hàng</h3>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/quan-ly-gio-hang" method="post">
            <input type="hidden" name="action" value="cap-nhat-trang-thai">
            <input type="hidden" id="editIdChiTiet" name="idGioHangChiTiet">
            <label>Tên người dùng:</label>
            <input type="text" id="editTenNguoiDung" readonly><br>
            <label>Tên sản phẩm:</label>
            <input type="text" id="editTenSanPham" readonly><br>
            <label>Số lượng:</label>
            <input type="number" name="soLuongMoi" id="editSoLuong" required min="1"><br>
            <label>Trạng thái:</label>
            <select name="trangThaiMoi" id="editTrangThai">
                <option value="dang_mua">Đang xử lý</option>
                <option value="da_dat_hang">Đã đặt hàng</option>
                <option value="da_huy">Hủy</option>
            </select><br>
            <button type="submit">Lưu</button>
            <button type="button" onclick="document.getElementById('editForm').classList.remove('active')">Hủy</button>
        </form>
    </div>
</body>
</html>