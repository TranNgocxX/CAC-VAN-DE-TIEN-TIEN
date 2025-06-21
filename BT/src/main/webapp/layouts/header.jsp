<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="BTL.models.NguoiDung, BTL.models.LoaiSanPham, BTL.models.GioHangChiTiet, BTL.daos.LoaiSanPhamDAO, java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    LoaiSanPhamDAO loaiDAO = new LoaiSanPhamDAO();
    List<LoaiSanPham> danhSachLoai = loaiDAO.layTatCa();
    NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    List<GioHangChiTiet> giohang = (List<GioHangChiTiet>) session.getAttribute("giohang");

    double tongTien = 0;
    int soLuongSanPham = 0;
    if (giohang != null) {
        for (GioHangChiTiet item : giohang) {
            tongTien += item.getSanPham().getGia() * item.getSoLuong();
            soLuongSanPham += item.getSoLuong();
        }
    }
%>

<style>
    .navbar-brand {
        font-size: 2.5rem;
        font-weight: 900;
        letter-spacing: 1px;
        color: #ffffff !important;
        text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
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
        min-height: 60px;
    }

    .cart-item:last-child {
        border-bottom: none;
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
            <ul class="navbar-nav mx-auto">
                <% for (LoaiSanPham loai : danhSachLoai) { %>
                    <li class="nav-item">
                        <a class="nav-link text-white px-3" href="<%= request.getContextPath() %>/trang-chu?idLoai=<%= loai.getId() %>">
                            <%= loai.getTenLoai() %>
                        </a>
                    </li>
                <% } %>
            </ul>

            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link text-white cart-icon" href="#" data-bs-toggle="dropdown">
                        🛒
                        <% if (soLuongSanPham > 0) { %>
                            <span class="cart-count"><%= soLuongSanPham %></span>
                        <% } %>
                    </a>
                    <div class="dropdown-menu dropdown-menu-end cart-dropdown">
                        <c:choose>
                            <c:when test="${not empty giohang}">
                                <c:forEach var="item" items="${giohang}">
                                    <div class="cart-item">
                                        <div class="cart-item-details">
                                            <div class="cart-item-name">${item.sanPham.tenSanPham}</div>
                                            <div class="cart-item-quantity">Số lượng: ${item.soLuong}</div>
                                        </div>
                                        <div class="cart-item-price">
                                            <fmt:formatNumber value="${item.sanPham.gia * item.soLuong}" type="currency" currencySymbol="₫"/>
                                        </div>
                                    </div>
                                </c:forEach>
                                <div class="cart-total">
                                    Tổng tiền: <fmt:formatNumber value="<%= tongTien %>" type="currency" currencySymbol="₫"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="cart-empty">Giỏ hàng trống</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </li>

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
</nav>

<%-- <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="BTL.models.NguoiDung, BTL.models.LoaiSanPham, BTL.models.GioHangChiTiet, BTL.daos.LoaiSanPhamDAO, java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    LoaiSanPhamDAO loaiDAO = new LoaiSanPhamDAO();
    List<LoaiSanPham> danhSachLoai = loaiDAO.layTatCa();
    NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    List<GioHangChiTiet> giohang = (List<GioHangChiTiet>) session.getAttribute("giohang");

    double tongTien = 0;
    int soLuongSanPham = 0;
    if (giohang != null) {
        for (GioHangChiTiet item : giohang) {
            tongTien += item.getSanPham().getGia() * item.getSoLuong();
            soLuongSanPham += item.getSoLuong();
        }
    }
%>

<style>
    .navbar-brand {
        font-size: 2.5rem;
        font-weight: 900;
        letter-spacing: 1px;
        color: #ffffff !important;
        text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
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
        min-height: 60px;
    }

    .cart-item:last-child {
        border-bottom: none;
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
            <ul class="navbar-nav mx-auto">
                <% for (LoaiSanPham loai : danhSachLoai) { %>
                    <li class="nav-item">
                        <a class="nav-link text-white px-3" href="<%= request.getContextPath() %>/trang-chu?idLoai=<%= loai.getId() %>">
                            <%= loai.getTenLoai() %>
                        </a>
                    </li>
                <% } %>
            </ul>

            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link text-white cart-icon" href="#" data-bs-toggle="dropdown">
                        🛒
                    </a>

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
</nav> --%>

<!--  -->
<!-- hêh --> 
<!--  -->
<%-- <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
	.navbar-brand {
	    font-size: 2.5rem;
	    font-weight: 900;
	    letter-spacing: 1px;
	    color: #ffffff !important;
	    text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
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
        min-height: 100px;       
    }

    .cart-item:last-child {
        border-bottom: none;
    }

    .cart-item img {
        width: 40px;
        height: 40px;
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
                        <span class="cart-count"><%= (giohang != null) ? giohang.size() : 0 %></span>
                    </a>
                    <div class="dropdown-menu dropdown-menu-end cart-dropdown">
                        <c:choose>
                            <c:when test="${not empty sessionScope.giohang}">
                                <c:forEach var="item" items="${sessionScope.giohang}">
                                    <div class="cart-item">
                                        <img src="<%= request.getContextPath() %>/images/${item.sanPham.anhUrl}" alt="${item.sanPham.tenSanPham}">
                                        <div class="cart-item-details">
                                            <div class="cart-item-name">${item.sanPham.tenSanPham}</div>
                                            <div class="cart-item-quantity">Số lượng: ${item.soLuong}</div>
                                        </div>
                                        <div class="cart-item-price">
                                            ₫<fmt:formatNumber value="${item.sanPham.gia * item.soLuong}" pattern="#,##0"/>
                                        </div>
                                    </div>
                                </c:forEach>
                                <hr>
                                <div class="cart-total">
                                    Tổng: ₫<fmt:formatNumber value="${sessionScope.giohang.stream().mapToDouble(item -> item.sanPham.gia * item.soLuong).sum()}" pattern="#,##0"/>
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
</nav>
 --%>