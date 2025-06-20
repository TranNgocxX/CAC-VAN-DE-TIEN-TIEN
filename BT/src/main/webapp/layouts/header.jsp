<%-- <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="BTL.models.NguoiDung" %>
<%@ page import="BTL.models.GioHang" %>
<%@ page import="BTL.models.SanPham" %>
<%@ page import="BTL.models.LoaiSanPham" %>
<%@ page import="BTL.daos.LoaiSanPhamDAO" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    LoaiSanPhamDAO loaiDAO = new LoaiSanPhamDAO();
    List<LoaiSanPham> danhSachLoai = loaiDAO.layTatCa();
    NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
%>

<style>
    .navbar-brand {
        font-size: 2rem; /* Larger brand logo */
        font-weight: bold;
    }
    .navbar-nav .nav-link {
        font-size: 1.7rem; 
        padding: 0.5rem 1rem; 
    }
    
    .nav-link.cart-icon {
        position: relative;
        padding-right: 1.8rem;
        transition: color 0.2s ease;
        font-size: 1.5rem;
    }

    .cart-icon:hover {
        color: #f06292 !important;
    }

    .cart-count {
        position: absolute;
        top: -5px;
        right: 0.5rem;
        background: #dc3545;
        color: white;
        border-radius: 50%;
        padding: 3px 8px;
        font-size: 0.7rem;
        font-weight: bold;
    }

    .cart-dropdown {
        width: 350px;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        background-color: #fff;
        padding: 1rem;
    }

    .cart-item {
        display: flex;
        align-items: center;
        padding: 0.5rem 0;
        border-bottom: 1px solid #f1f1f1;        
    }

    .cart-item:last-child {
        border-bottom: none;
    }

    .cart-item img {
        width: 50px;
        height: 50px;
        object-fit: cover;
        border-radius: 4px;
        margin-right: 1rem;
    }

    .cart-item-details {
        flex-grow: 1;
    }

    .cart-item-name {
        font-size: 0.9rem;
        font-weight: 600;
        color: #333;
        margin-bottom: 0.2rem;
    }

    .cart-item-quantity {
        font-size: 0.8rem;
        color: #666;
    }

    .cart-item-price {
        font-size: 0.9rem;
        font-weight: bold;
        color: #c2185b;
    }

    .cart-total {
        font-size: 1rem;
        font-weight: bold;
        color: #333;
        margin: 0.5rem 0;
    }

    .btn-checkout {
        background-color: #f06292;
        color: white;
        border: none;
        font-size: 0.9rem;
        padding: 0.5rem;
        transition: background-color 0.2s ease;
    }

    .btn-checkout:hover {
        background-color: #d81b60;
    }

    .cart-empty {
        font-size: 0.9rem;
        color: #666;
        text-align: center;
        padding: 1rem;
    }
</style>

<nav class="navbar navbar-expand-lg" style="background-color: #f8bbd0;">
    <div class="container-fluid px-4">
        <a class="navbar-brand text-white fw-bold" href="<%= request.getContextPath() %>/trang-chu">Girlss</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-between" id="navbarHeader">
            <!-- Menu giữa -->
            <ul class="navbar-nav mx-auto">
                <% for (LoaiSanPham loai : danhSachLoai) { %>
                    <li class="nav-item">
                        <a class="nav-link text-white px-3" href="<%= request.getContextPath() %>/trang-chu?idLoai=<%= loai.getId() %>">
                            <%= loai.getTenLoai() %>
                        </a>
                    </li>
                <% } %>
            </ul>

            <!-- Phần phải: Giỏ hàng + Người dùng -->
            <ul class="navbar-nav">
                <!-- Giỏ hàng -->
                <li class="nav-item dropdown">
                    <a class="nav-link text-white cart-icon" href="#" data-bs-toggle="dropdown">
                        🛒
                        <span class="cart-count">
                            <c:out value="${sessionScope.danhSachGioHang != null ? sessionScope.danhSachGioHang.size() : 0}"/>
                        </span>
                    </a>
                    <div class="dropdown-menu dropdown-menu-end cart-dropdown">
                        <c:choose>
                            <c:when test="${not empty sessionScope.danhSachGioHang}">
                                <c:forEach var="item" items="${sessionScope.danhSachGioHang}">
                                    <div class="cart-item">
                                        <img src="<%= request.getContextPath() %>/images/${item.sanPham.anhUrl}" alt="${item.sanPham.tenSanPham}">
                                        <div class="cart-item-details">
                                            <div class="cart-item-name">${item.sanPham.tenSanPham}</div>
                                            <div class="cart-item-quantity">Số lượng: ${item.soLuong}</div>
                                        </div>
                                        <div class="cart-item-price">₫<c:out value="${String.format('%,.0f', item.sanPham.gia * item.soLuong)}"/></div>
                                    </div>
                                </c:forEach>
                                <hr>
                                <div class="cart-total">
                                    Tổng: ₫<c:out value="${String.format('%,.0f', sessionScope.danhSachGioHang.stream().mapToDouble(item -> item.sanPham.gia * item.soLuong).sum())}"/>
                                </div>
                                <a href="<%= request.getContextPath() %>/gio-hang" class="btn btn-checkout w-100">Xem giỏ hàng</a>
                                <a href="<%= request.getContextPath() %>/thanh-toan" class="btn btn-checkout w-100 mt-2">Thanh toán</a>
                            </c:when>
                            <c:otherwise>
                                <p class="cart-empty">Chưa có sản phẩm nào trong giỏ</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </li>

                <!-- Người dùng -->
                <% if (nguoiDung == null) { %>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="<%= request.getContextPath() %>/auth/dang-nhap">Đăng nhập</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="<%= request.getContextPath() %>/auth/dang-ky">Đăng ký</a>
                    </li>
                <% } else { %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown">
                            Xin chào, <%= nguoiDung.getHoTen() %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="<%= request.getContextPath() %>/auth/thong-tin">Thông tin cá nhân</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="<%= request.getContextPath() %>/san-pham/quan-ly">Quản lý sản phẩm</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="<%= request.getContextPath() %>/quan-ly-gio-hang">Quản lý giỏ hàng</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="<%= request.getContextPath() %>/auth/dang-xuat">Đăng xuất</a></li>
                        </ul>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>  --%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="BTL.models.NguoiDung" %>
<%@ page import="BTL.models.SanPham" %>
<%@ page import="BTL.models.LoaiSanPham" %>
<%@ page import="BTL.models.GioHangChiTiet" %>
<%@ page import="BTL.daos.LoaiSanPhamDAO" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    LoaiSanPhamDAO loaiDAO = new LoaiSanPhamDAO();
    List<LoaiSanPham> danhSachLoai = loaiDAO.layTatCa();
    NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    List<GioHangChiTiet> giohang = (List<GioHangChiTiet>) session.getAttribute("giohang");

    double tongTien = 0;
    if (giohang != null) {
        for (GioHangChiTiet item : giohang) {
            tongTien += item.getSanPham().getGia() * item.getSoLuong();
        }
    }
%>

<style>
/* ... giữ nguyên phần CSS hiện có của bạn ... */
</style>

<nav class="navbar navbar-expand-lg" style="background-color: #f8bbd0;">
    <div class="container-fluid px-4">
        <a class="navbar-brand text-white fw-bold" href="<%= request.getContextPath() %>/trang-chu">Girlss</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-between" id="navbarHeader">
            <!-- Menu giữa -->
            <ul class="navbar-nav mx-auto">
                <% for (LoaiSanPham loai : danhSachLoai) { %>
                    <li class="nav-item">
                        <a class="nav-link text-white px-3" href="<%= request.getContextPath() %>/trang-chu?idLoai=<%= loai.getId() %>">
                            <%= loai.getTenLoai() %>
                        </a>
                    </li>
                <% } %>
            </ul>

            <!-- Phần phải: Giỏ hàng + Người dùng -->
            <ul class="navbar-nav">
                <!-- Giỏ hàng -->
                <li class="nav-item dropdown">
                    <a class="nav-link text-white cart-icon" href="#" data-bs-toggle="dropdown">
                        🛒
                        <span class="cart-count"><%= (giohang != null) ? giohang.size() : 0 %></span>
                    </a>

                    <div class="dropdown-menu dropdown-menu-end cart-dropdown">
                        <% if (giohang != null && !giohang.isEmpty()) { %>
                            <% for (GioHangChiTiet item : giohang) { %>
                                <div class="cart-item">
                                    <img src="<%= request.getContextPath() %>/images/<%= item.getSanPham().getAnhUrl() %>" alt="<%= item.getSanPham().getTenSanPham() %>">
                                    <div class="cart-item-details">
                                        <div class="cart-item-name"><%= item.getSanPham().getTenSanPham() %></div>
                                        <div class="cart-item-quantity">Số lượng: <%= item.getSoLuong() %></div>
                                    </div>
                                    <div class="cart-item-price">
                                        ₫<%= String.format("%,.0f", item.getSanPham().getGia() * item.getSoLuong()) %>
                                    </div>
                                </div>
                            <% } %>
                            <hr>
                            <div class="cart-total">
                                Tổng: ₫<%= String.format("%,.0f", tongTien) %>
                            </div>
                            <a href="<%= request.getContextPath() %>/gio-hang" class="btn btn-checkout w-100">Xem giỏ hàng</a>
                            <a href="<%= request.getContextPath() %>/thanh-toan" class="btn btn-checkout w-100 mt-2">Thanh toán</a>
                        <% } else { %>
                            <p class="cart-empty">Chưa có sản phẩm nào trong giỏ</p>
                        <% } %>
                    </div>
                </li>

                <!-- Người dùng -->
                <% if (nguoiDung == null) { %>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="<%= request.getContextPath() %>/auth/dang-nhap">Đăng nhập</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="<%= request.getContextPath() %>/auth/dang-ky">Đăng ký</a>
                    </li>
                <% } else { %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown">
                            Xin chào, <%= nguoiDung.getHoTen() %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="<%= request.getContextPath() %>/auth/thong-tin">Thông tin cá nhân</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="<%= request.getContextPath() %>/san-pham/quan-ly">Quản lý sản phẩm</a></li>
                            <li><a class="dropdown-item" href="<%= request.getContextPath() %>/quan-ly-gio-hang">Quản lý giỏ hàng</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="<%= request.getContextPath() %>/auth/dang-xuat">Đăng xuất</a></li>
                        </ul>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav> 
