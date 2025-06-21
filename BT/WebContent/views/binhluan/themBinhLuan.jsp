<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- HIỂN THỊ DANH SÁCH BÌNH LUẬN -->
<p><strong>Bình luận:</strong></p>
<div class="mt-3 mb-4">
    <c:forEach var="bl" items="${danhSachBinhLuan}">
        <div class="border rounded p-3 mb-2 bg-white shadow-sm">
            <p class="mb-1 fw-bold text-primary">${bl.nguoiDung.hoTen}
                <span class="text-muted small">(${bl.ngayTao})</span>
            </p>
            <p class="mb-0">${bl.noiDung}</p>
        </div>
    </c:forEach>

    <c:if test="${empty danhSachBinhLuan}">
        <p class="text-muted">Chưa có bình luận nào.</p>
    </c:if>
</div>

<!-- FORM THÊM BÌNH LUẬN -->
<c:if test="${not empty sessionScope.nguoiDung}">
    <form action="${pageContext.request.contextPath}/binh-luan" method="post">
        <input type="hidden" name="idSanPham" value="${sp.id}" />
        <div class="mb-3">
            <textarea name="noiDung" class="form-control" rows="2" placeholder="Nhập bình luận..." required></textarea>
        </div>
        <button type="submit" class="btn btn-outline-danger">Gửi bình luận</button>
    </form>
</c:if>
