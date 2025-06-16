<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card">
    <div class="card-header">
        <h3>Thông tin cá nhân</h3>
    </div>
    <div class="card-body">
        <div class="row">
            <div class="col-md-8">
                <table class="table table-bordered">
                    <tr>
                        <th width="30%">Tên đăng nhập</th>
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
                        <td>${nguoiDung.ngayTao}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
