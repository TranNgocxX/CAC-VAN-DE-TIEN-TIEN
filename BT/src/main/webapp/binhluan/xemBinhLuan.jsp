<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h3>Danh sách bình luận</h3>
        <a href="${pageContext.request.contextPath}/binh-luan/them?idSanPham=${dsBinhLuan[0].idSanPham}" 
           class="btn btn-primary btn-sm">Thêm bình luận</a>
    </div>
    <div class="card-body">
        <c:if test="${empty dsBinhLuan}">
            <div class="alert alert-info">Chưa có bình luận nào</div>
        </c:if>
        
        <c:forEach items="${dsBinhLuan}" var="binhLuan">
            <div class="card mb-3">
                <div class="card-header bg-light">
                    <strong>${binhLuan.nguoiDung.hoTen}</strong>
                    <span class="text-muted float-end">${binhLuan.ngayTao}</span>
                </div>
                <div class="card-body">
                    <p class="card-text">${binhLuan.noiDung}</p>
                </div>
            </div>
        </c:forEach>
        
        <div class="mt-3">
            <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-secondary">Quay lại</a>
        </div>
    </div>
</div>