<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
    .bg-thongtin {
        background-color: #f8d3e0; /* Màu hồng nhạt giống trang đăng ký */
        color: #333;
    }
    .table th {
        width: 30%;
    }
</style>

<div class="card">
    <div class="card-header bg-thongtin">
        <h3>Thông tin cá nhân</h3>
    </div>
    <div class="card-body">
        <div class="row">
            <div class="col-md-8">
                <table class="table table-bordered">
                    <tr>
                        <th>Tên đăng nhập</th>
                        <td>${nguoiDung.tenDangNhap}</td>
                    </tr>
                    <tr>
                        <th>Họ tên</th>
                        <td>${nguoiDung.hoTen}</td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td>${nguoiDung.email}</td>
                    </tr>
                    <tr>
                        <th>Số điện thoại</th>
                        <td>${nguoiDung.soDienThoai}</td>
                    </tr>
                    <tr>
                        <th>Vai trò</th>
                        <td>${nguoiDung.vaiTro}</td>
                    </tr>
                    <tr>
                        <th>Ngày tạo</th>
                        <td><fmt:formatDate value="${nguoiDung.ngayTao}" pattern="dd/MM/yyyy HH:mm" /></td>
                    </tr>
                </table>
                
                <div class="mt-3">
                    <a href="${pageContext.request.contextPath}/auth/dang-xuat" class="btn btn-danger">Đăng xuất</a>
                </div>
            </div>
        </div>
    </div>
</div>