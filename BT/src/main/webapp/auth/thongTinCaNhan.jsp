<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
<hr />
<h4 class="mt-4">Gửi bình luận đến hệ thống</h4>
<form action="${pageContext.request.contextPath}/binh-luan/them" method="post">
    <input type="hidden" name="idSanPham" value="0" />
    <div class="mb-3">
        <textarea class="form-control" name="noiDung" placeholder="Nhập nội dung bình luận..." required rows="3"></textarea>
    </div>
    <button type="submit" class="btn btn-success">Gửi bình luận</button>
</form>

<c:if test="${not empty dsBinhLuan}">
    <h5 class="mt-4">Các bình luận bạn đã gửi:</h5>
    <c:forEach var="bl" items="${dsBinhLuan}">
        <div class="border rounded p-2 mb-2">
            <small class="text-muted">Ngày gửi: ${bl.ngayTao}</small>
            <p class="mb-0">${bl.noiDung}</p>
        </div>
    </c:forEach>
</c:if>

