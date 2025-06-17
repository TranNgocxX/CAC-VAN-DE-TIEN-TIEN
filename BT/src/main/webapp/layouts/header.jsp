<%@ page import="BTL.models.NguoiDung" %>
<%@ page import="BTL.models.LoaiSanPham" %>
<%@ page import="BTL.daos.LoaiSanPhamDAO" %>
<%@ page import="BTL.models.SanPham" %>
<%@ page import="BTL.daos.SanPhamDAO" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    LoaiSanPhamDAO loaiDAO = new LoaiSanPhamDAO();
    List<LoaiSanPham> danhSachLoai = loaiDAO.layTatCa();

    SanPhamDAO sanPhamDAO = new SanPhamDAO();
    List<SanPham> danhSachSanPham = sanPhamDAO.layTatCa();

    NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");

    // Xử lý phân trang
    int pageSize = 6;
    int currentPage = 1;

    String pageParam = request.getParameter("page");
    if (pageParam != null) {
        try {
            currentPage = Integer.parseInt(pageParam);
        } catch (NumberFormatException ignored) {}
    }

    int totalProducts = danhSachSanPham.size();
    int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

    int startIndex = (currentPage - 1) * pageSize;
    int endIndex = Math.min(startIndex + pageSize, totalProducts);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BTL Shop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .navbar-nav .nav-link.active {
            font-weight: bold;
        }
        .card-img-top {
            height: 250px;
            object-fit: cover;
        }
        .card {
            border: none;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .footer {
            background-color: #212529;
            color: #fff;
            padding: 30px 0;
            text-align: center;
            margin-top: 50px;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/trang-chu">Girlss</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <% for (LoaiSanPham loai : danhSachLoai) { %>
                        <li class="nav-item">
                            <a class="nav-link" href="<%= request.getContextPath() %>/trang-chu?idLoai=<%= loai.getId() %>">
                                <%= loai.getTenLoai() %>
                            </a>
                        </li>
                    <% } %>
                </ul>
                <ul class="navbar-nav">
                    <% if (nguoiDung == null) { %>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/auth/dang-nhap">Đăng nhập</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/auth/dang-ky">Đăng ký</a>
                        </li>
                    <% } else { %>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" 
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Xin chào, <%= nguoiDung.getHoTen() %>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/thong-tin">Thông tin cá nhân</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/san-pham/quan-ly">Quản lý sản phẩm</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/dang-xuat">Đăng xuất</a></li>
                            </ul>
                        </li>
                    <% } %>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Danh sách sản phẩm -->
    <div class="container mt-4">
        <div class="row">
            <% for (int i = startIndex; i < endIndex; i++) {
                   SanPham sp = danhSachSanPham.get(i);
            %>
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <img src="<%= sp.getAnhUrl() %>" class="card-img-top" alt="<%= sp.getTenSanPham() %>">
                        <div class="card-body">
                            <h5 class="card-title"><%= sp.getTenSanPham() %></h5>
                            <p class="card-text text-danger fw-bold">₫<%= String.format("%,.0f", sp.getGia()) %></p>
							<a href="<%= request.getContextPath() %>/san-pham/chi-tiet?id=<%= sp.getId() %>" class="btn btn-primary">Xem chi tiết</a>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>

        <!-- Thanh phân trang -->
        <nav aria-label="Phân trang">
            <ul class="pagination justify-content-center">
                <% if (currentPage > 1) { %>
                    <li class="page-item">
						<a class="page-link" href="<%= request.getContextPath() %>/trang-chu?page=<%= currentPage - 1 %>">«</a>
                    </li>
                <% } else { %>
                    <li class="page-item disabled"><span class="page-link">«</span></li>
                <% } %>

                <% for (int i = 1; i <= totalPages; i++) { %>
                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
						<a class="page-link" href="<%= request.getContextPath() %>/trang-chu?page=<%= i %>"><%= i %></a>
                    </li>
                <% } %>

                <% if (currentPage < totalPages) { %>
                    <li class="page-item">
                        <a class="page-link" href="?page=<%= currentPage + 1 %>">»</a>
                    </li>
                <% } else { %>
                    <li class="page-item disabled"><span class="page-link">»</span></li>
                <% } %>
            </ul>
        </nav>
    </div>

    <!-- Footer -->
    <div class="footer">
        <p>&copy; 2025 GirlssShop. All rights reserved.</p>
        <p>Email: lienhe@myphamshop.vn | Hotline: 1900 1234</p>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>






<%-- <%@ page import="BTL.models.NguoiDung" %>
<%@ page import="BTL.models.LoaiSanPham" %>
<%@ page import="BTL.daos.LoaiSanPhamDAO" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>

<%
    // Lấy danh sách loại sản phẩm từ DAO
    LoaiSanPhamDAO loaiDAO = new LoaiSanPhamDAO();
    List<LoaiSanPham> danhSachLoai = loaiDAO.layTatCa();
    NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .navbar-nav .nav-link.active {
            font-weight: bold;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/trang-chu">BTL Shop</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">

                <!-- Danh sách loại sản phẩm -->
                <ul class="navbar-nav me-auto">
                    <% for (LoaiSanPham loai : danhSachLoai) { %>
                        <li class="nav-item">
                            <a class="nav-link" 
                               href="<%= request.getContextPath() %>/trang-chu?idLoai=<%= loai.getId() %>">
                                <%= loai.getTenLoai() %>
                            </a>
                        </li>
                    <% } %>
                </ul>

                <!-- Đăng nhập / Xin chào -->
                <ul class="navbar-nav">
                    <% if (nguoiDung == null) { %>
                        <li class="nav-item">
                            <a class="nav-link ${active eq 'dang-nhap' ? 'active' : ''}" 
                               href="${pageContext.request.contextPath}/auth/dang-nhap">Đăng nhập</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${active eq 'dang-ky' ? 'active' : ''}" 
                               href="${pageContext.request.contextPath}/auth/dang-ky">Đăng ký</a>
                        </li>
                    <% } else { %>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" 
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Xin chào, <%= nguoiDung.getHoTen() %>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/thong-tin">Thông tin cá nhân</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/san-pham/quan-ly">Quản lý sản phẩm</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/dang-xuat">Đăng xuất</a></li>
                            </ul>
                        </li>
                    <% } %>
                </ul>

            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <jsp:include page="${content}" />
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> --%>





<%-- <%@ page import="BTL.models.NguoiDung" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .navbar-nav .nav-link.active {
            font-weight: bold;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/san-pham">BTL Shop</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link ${active eq 'san-pham' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/san-pham">Sản phẩm</a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <% 
                        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
                        if (nguoiDung == null) { 
                    %>
                        <li class="nav-item">
                            <a class="nav-link ${active eq 'dang-nhap' ? 'active' : ''}" 
                               href="${pageContext.request.contextPath}/auth/dang-nhap">Đăng nhập</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${active eq 'dang-ky' ? 'active' : ''}" 
                               href="${pageContext.request.contextPath}/auth/dang-ky">Đăng ký</a>
                        </li>
                    <% } else { %>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" 
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Xin chào, <%= nguoiDung.getHoTen() %>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/thong-tin">Thông tin cá nhân</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/san-pham/quan-ly">Quản lý sản phẩm</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/dang-xuat">Đăng xuất</a></li>
                            </ul>
                        </li>
                    <% } %>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <jsp:include page="${content}" />
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
 --%>