<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card">
    <div class="card-header">
        <h3>Thêm bình luận</h3>
    </div>
    <div class="card-body">
        <c:if test="${not empty loi}">
            <div class="alert alert-danger">${loi}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/binh-luan/them" method="post">
            <input type="hidden" name="idSanPham" value="${idSanPham}">
            
            <div class="mb-3">
                <label for="noiDung" class="form-label">Nội dung bình luận</label>
                <textarea class="form-control" id="noiDung" name="noiDung" rows="5" required></textarea>
            </div>
            
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-secondary me-md-2">Quay lại</a>
                <button type="submit" class="btn btn-primary">Gửi bình luận</button>
            </div>
        </form>
    </div>
</div>