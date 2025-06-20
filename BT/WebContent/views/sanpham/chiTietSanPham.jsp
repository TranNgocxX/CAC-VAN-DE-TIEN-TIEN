<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="BTL.models.SanPham" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    SanPham sp = (SanPham) request.getAttribute("sp");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title><%= sp.getTenSanPham() %> - Chi tiết sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@400;600&display=swap" rel="stylesheet">
    <style>
        body {
            background-color: #fff0f5;
            font-family: 'Quicksand', sans-serif;
        }

        .product-image {
            border-radius: 12px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
            transition: transform 0.3s ease;
        }

        .product-image:hover {
            transform: scale(1.03);
        }

        .product-title {
            color: #d81b60;
            font-weight: 600;
        }

        .product-price {
            color: #c2185b;
            font-size: 1.7rem;
            font-weight: bold;
        }

        .product-detail {
            background-color: #fce4ec;
            border-radius: 10px;
            padding: 1rem 1.2rem;
        }

        .btn-back {
            background-color: #f8bbd0;
            color: white;
            border: none;
            transition: background-color 0.3s ease;
        }

        .btn-back:hover {
            background-color: #ec407a;
        }

        .btn-add-to-cart {
            background-color: #f06292;
            color: white;
            border: none;
            transition: all 0.3s ease;
        }

        .btn-add-to-cart:hover {
            background-color: #d81b60;
            transform: scale(1.05);
        }

        .comment-box {
            background-color: #ffffff;
            border-radius: 10px;
            padding: 1rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .no-products {
            text-align: center;
            color: #6c757d;
            font-style: italic;
        }

        .toast-success {
            background-color: #28a745 !important;
            color: white !important;
            min-width: 300px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.2);
        }

        @media (max-width: 768px) {
            .input-group {
                flex-direction: column;
            }

            .input-group .form-control {
                margin-bottom: 10px;
            }
        }
    </style>
</head>
<body>
<%@ include file="/layouts/header.jsp" %>

<div class="container mt-5">
    <!-- Hiển thị thông báo thành công hoặc lỗi -->
    <% if (session.getAttribute("success") != null) { %>
        <div class="alert alert-success mt-3">
            <%= session.getAttribute("success") %>
            <% session.removeAttribute("success"); %>
        </div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger mt-3">
            <%= request.getAttribute("error") %>
        </div>
    <% } %>

    <div class="row align-items-start gx-5">
        <div class="col-md-6 mb-4">
            <img src="<%= request.getContextPath() %>/images/<%= sp.getAnhUrl() %>" class="img-fluid product-image" alt="<%= sp.getTenSanPham() %>">
        </div>

        <div class="col-md-6">
            <h2 class="product-title mb-3"><%= sp.getTenSanPham() %></h2>
            <p class="product-price mb-3">₫<%= String.format("%,.0f", sp.getGia()) %></p>

            <p class="fw-bold">Mô tả chi tiết:</p>
            <div class="product-detail mb-4">
                <%= sp.getMoTa() %>
            </div>

            <p><strong>Số lượng còn:</strong> <%= sp.getSoLuong() %></p>

            <% if (sp.getSoLuong() > 0) { %>
                <form id="cartForm" action="<%= request.getContextPath() %>/gio-hang" method="post">
                    <input type="hidden" name="idSanPham" value="<%= sp.getId() %>">
                    <div class="input-group mb-4" style="max-width: 300px;">
                        <input type="number" id="soLuong" name="soLuong" class="form-control" value="1" min="1" max="<%= sp.getSoLuong() %>" required>
                        <button type="submit" class="btn btn-add-to-cart" onclick="hienThongBaoThanhCong()">Thêm vào giỏ hàng</button>
                    </div>
                </form>

                <div id="thongBaoThanhCong" class="toast toast-success position-fixed top-50 start-50 translate-middle z-3 shadow p-3" role="alert" style="display: none;">
                    <div class="d-flex justify-content-between align-items-center">
                        <span>Thêm sản phẩm thành công!</span>
                        <button type="button" class="btn-close btn-close-white ms-3" aria-label="Close" onclick="anThongBao()"></button>
                    </div>
                </div>
            <% } else { %>
                <p class="text-danger">Hết hàng</p>
            <% } %>

            <hr>

            <p class="fw-bold">Bình luận:</p>
            <div class="mt-3 mb-4">
                <c:forEach var="bl" items="${danhSachBinhLuan}">
                    <div class="border rounded p-3 mb-3 bg-white shadow-sm position-relative" id="bl-${bl.id}">
                        <div class="d-flex justify-content-between align-items-start">
                            <div>
                                <p class="fw-semibold text-primary mb-1">
                                    ${bl.nguoiDung.hoTen}
                                    <span class="text-muted small">(${bl.ngayTao})</span>
                                </p>
                            </div>

                            <c:if test="${sessionScope.nguoiDung != null && sessionScope.nguoiDung.id == bl.nguoiDung.id}">
                                <div class="dropdown">
                                    <button class="btn btn-sm btn-light border dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">...</button>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a class="dropdown-item sua-binh-luan" href="#" data-id="${bl.id}">Sửa</a>
                                        </li>
                                        <li>
                                            <form action="${pageContext.request.contextPath}/binh-luan" method="post" onsubmit="return confirm('Bạn có chắc chắn muốn xóa bình luận này?')">
                                                <input type="hidden" name="action" value="delete" />
                                                <input type="hidden" name="idBinhLuan" value="${bl.id}" />
                                                <input type="hidden" name="idSanPham" value="${sp.id}" />
                                                <button type="submit" class="dropdown-item text-danger w-100 text-start">Xóa</button>
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                            </c:if>
                        </div>

                        <!-- Nội dung -->
                        <p class="mb-0 noi-dung">${bl.noiDung}</p>

                        <!-- Form sửa -->
                        <form class="form-sua mt-2" action="${pageContext.request.contextPath}/binh-luan" method="post" style="display: none;">
                            <input type="hidden" name="action" value="edit" />
                            <input type="hidden" name="idBinhLuan" value="${bl.id}" />
                            <input type="hidden" name="idSanPham" value="${sp.id}" />
                            <div class="input-group">
                                <input type="text" name="noiDung" class="form-control" value="${bl.noiDung}" required />
                                <button type="submit" class="btn btn-sm btn-success">Lưu</button>
                                <button type="button" class="btn btn-sm btn-secondary btn-huy" data-id="${bl.id}">Hủy</button>
                            </div>
                        </form>
                    </div>
                </c:forEach>

                <c:if test="${empty danhSachBinhLuan}">
                    <p class="no-products">Chưa có bình luận nào.</p>
                </c:if>
            </div>

            <form action="${pageContext.request.contextPath}/binh-luan" method="post">
                <input type="hidden" name="idSanPham" value="<%= sp.getId() %>" />
                <div class="mb-3">
                    <textarea name="noiDung" class="form-control" rows="3" placeholder="Nhập bình luận..." required></textarea>
                </div>
                <button type="submit" class="btn btn-outline-danger">Gửi bình luận</button>
            </form>

            <a href="<%= request.getContextPath() %>/trang-chu" class="btn btn-back mt-4">← Quay lại trang chủ</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Ngăn dropdown lan ra ngoài
    document.querySelectorAll('.dropdown-menu').forEach(function(menu) {
        menu.addEventListener('click', function(e) {
            e.stopPropagation();
        });
    });

    // Mở form sửa
    document.querySelectorAll(".sua-binh-luan").forEach(function(link) {
        link.addEventListener("click", function(e) {
            e.preventDefault();
            const id = this.dataset.id;
            const container = document.getElementById("bl-" + id);
            const text = container.querySelector(".noi-dung");
            const form = container.querySelector(".form-sua");

            text.style.display = "none";
            form.style.display = "block";
        });
    });

    // Hủy sửa
    document.querySelectorAll(".btn-huy").forEach(function(btn) {
        btn.addEventListener("click", function() {
            const id = this.dataset.id;
            const container = document.getElementById("bl-" + id);
            const text = container.querySelector(".noi-dung");
            const form = container.querySelector(".form-sua");

            form.style.display = "none";
            text.style.display = "block";
        });
    });

    function hienThongBaoThanhCong() {
        const toast = document.getElementById("thongBaoThanhCong");
        toast.style.display = "block";
        toast.classList.add("show");

        setTimeout(() => {
            toast.style.display = "none";
            toast.classList.remove("show");
        }, 3000);
    }

    function anThongBao() {
        const toast = document.getElementById("thongBaoThanhCong");
        toast.style.display = "none";
        toast.classList.remove("show");
    }

    // Xử lý submit form bằng AJAX để hiển thị thông báo mà không tải lại trang
    document.getElementById("cartForm")?.addEventListener("submit", function(e) {
        e.preventDefault();
        const form = this;
        const formData = new FormData(form);

        fetch(form.action, {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                hienThongBaoThanhCong();
            } else {
                alert("Có lỗi xảy ra: " + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("Đã có lỗi xảy ra khi thêm vào giỏ hàng!");
        });
    });
</script>
</body>
</html>