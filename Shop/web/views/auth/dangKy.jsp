<%@ page import="javax.servlet.http.HttpServletRequest" %><%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <div class="card-header">
                <h3 class="text-center">Đăng ký tài khoản</h3>
            </div>
            <div class="card-body">
                <c:if test="${not empty loi}">
                    <div class="alert alert-danger">${loi}</div>
                </c:if>
                
                <form method="post" action="${pageContext.request.contextPath}/auth/dang-ky">
                    <div class="mb-3">
                        <label for="tenDangNhap" class="form-label">Tên đăng nhập</label>
                        <input type="text" class="form-control" id="tenDangNhap" name="tenDangNhap" required>
                    </div>
                    <div class="mb-3">
                        <label for="matKhau" class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control" id="matKhau" name="matKhau" required>
                    </div>
                    <div class="mb-3">
                        <label for="hoTen" class="form-label">Họ tên</label>
                        <input type="text" class="form-control" id="hoTen" name="hoTen" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="soDienThoai" class="form-label">Số điện thoại</label>
                        <input type="tel" class="form-control" id="soDienThoai" name="soDienThoai" required>
                    </div>
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary">Đăng ký</button>
                    </div>
                </form>
                
                <div class="mt-3 text-center">
                    <p>Đã có tài khoản? <a href="${pageContext.request.contextPath}/auth/dang-nhap">Đăng nhập ngay</a></p>
                </div>
            </div>
        </div>
    </div>
</div>