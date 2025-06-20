<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    /* CSS giống như trang đăng ký */
    .auth-container {
        max-width: 500px;
        margin: 2rem auto;
        padding: 2rem;
        border-radius: 10px;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        background-color: #ffffff;
    }
    .auth-header {
        background-color: #f8d3e0;
        color: #333;
        padding: 1rem;
        border-radius: 8px 8px 0 0 !important;
        text-align: center;
        margin-bottom: 1.5rem;
    }
    .auth-header h3 {
        margin: 0;
        font-weight: 600;
    }
    .auth-card {
        border: none;
        border-radius: 10px;
        overflow: hidden;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
    .auth-btn {
        background-color: #f06292;
        border: none;
        padding: 10px;
        font-weight: 500;
        transition: all 0.3s;
    }
    .auth-btn:hover {
        background-color: #e91e63;
        transform: translateY(-2px);
    }
    .auth-link {
        color: #f06292;
        text-decoration: none;
        font-weight: 500;
    }
    .auth-link:hover {
        color: #e91e63;
        text-decoration: underline;
    }
    .form-control:focus {
        border-color: #f8d3e0;
        box-shadow: 0 0 0 0.25rem rgba(248, 211, 224, 0.25);
    }
</style>

<div class="auth-container">
    <div class="auth-card">
        <div class="auth-header">
            <h3>Đăng nhập</h3>
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
                    <button type="submit" class="btn auth-btn">Đăng nhập</button>
                </div>
            </form>
            
            <div class="mt-3 text-center">
                <p>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/auth/dang-ky" class="auth-link">Đăng ký ngay</a></p>
            </div>
        </div>
    </div>
</div>