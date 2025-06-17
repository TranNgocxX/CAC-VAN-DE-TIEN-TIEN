<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row mb-4">
    <c:forEach var="loai" items="${danhSachLoai}">
        <div class="col-auto">
            <a href="${pageContext.request.contextPath}/trang-chu?idLoai=${loai.id}" class="btn btn-outline-primary">
                ${loai.tenLoai}
            </a>
        </div>
    </c:forEach>
</div>

<div class="row">
    <c:forEach var="sp" items="${danhSachSanPham}">
        <div class="col-md-3 mb-4">
            <div class="card h-100">
                <img src="${sp.anhUrl}" class="card-img-top" alt="${sp.tenSanPham}" style="height:200px; object-fit:cover;">
                <div class="card-body">
                    <h5 class="card-title">${sp.tenSanPham}</h5>
                    <p class="card-text text-truncate">${sp.moTa}</p>
                    <p class="card-text fw-bold text-danger"><fmt:formatNumber value="${sp.gia}" type="currency" currencySymbol="₫"/></p>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
