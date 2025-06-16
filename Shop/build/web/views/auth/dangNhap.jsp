<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <div class="card-header">
                <h3 class="text-center">Đăng nhập</h3>
            </div>
            <div class="card-body">
                <c:if test="${not empty loi}">
                    <div class="alert alert-danger">${loi}</div>
                </c:if>
                <c:if test="${not empty thanhCong}">
                    <div class="alert alert-success">${thanhCong}</div>
                </c:if>
                
                <form action="${pageContext.request.contextPath}/auth/dang-nhap" method="post">
                    <div class="mb-3">
                        <label for="tenDangNhap" class="form-label">Tên đăng nhập</label>
                        <input type="text" class="form-control" id="tenDangNhap" name="tenDangNhap" required>
                    </div>
                    <div class="mb-3">
                        <label for="matKhau" class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control" id="matKhau" name="matKhau" required>
                    </div>
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary">Đăng nhập</button>
                    </div>
                </form>
                
                <div class="mt-3 text-center">
                    <p>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/auth/dang-ky">Đăng ký ngay</a></p>
                </div>
            </div>
        </div>
    </div>
</div>