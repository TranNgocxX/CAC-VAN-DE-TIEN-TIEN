<%@ page import="BTL.models.SanPham" %>
<%@ page import="BTL.daos.SanPhamDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%
    SanPhamDAO sanPhamDAO = new SanPhamDAO();
    List<SanPham> danhSachSanPham = sanPhamDAO.layTatCa();

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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@400;600&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Quicksand', sans-serif;
            background-color: #fefefe;
        }

        .navbar {
            background-color: #f8bbd0;
        }

        .navbar .navbar-brand {
            font-weight: bold;
            font-size: 3.2rem;
            color: white;
        }

        .navbar-nav .nav-link {
            color: white !important;
            margin: 0 10px;
        }

        .navbar-nav .nav-link:hover {
            text-decoration: underline;
        }

        .card {
		    border: none;
		    box-shadow: 0 0 8px rgba(0,0,0,0.1);
		    transition: transform 0.2s;
		    height: 100%; 
		    padding-bottom: 10px; 
        }

        .card:hover {
            transform: scale(1.02);
        }

        .card-img-top {
            height: 280px;
            object-fit: cover;
        }

        .footer {
            background-color: #f8bbd0;
            color: white;
            text-align: center;
            padding: 30px 0;
        }

        .pagination .page-item.active .page-link {
            background-color: #f06292;
            border-color: #f06292;
            color: #fff;
            font-weight: bold;
        }

        .pagination .page-link {
            color: #f06292;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

<%@ include file="/layouts/header.jsp" %>

<main class="flex-grow-1">
    <div class="container mt-5">
        <div class="row">
            <% for (int i = startIndex; i < endIndex; i++) {
                SanPham sp = danhSachSanPham.get(i);
            %>
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <img src="<%= request.getContextPath() %>/images/<%= sp.getAnhUrl() %>" class="card-img-top" alt="<%= sp.getTenSanPham() %>">
                        <div class="card-body">
                            <h5 class="card-title"><%= sp.getTenSanPham() %></h5>
                            <p class="card-text text-danger fw-bold">₫<%= String.format("%,.0f", sp.getGia()) %></p>
                            <a href="<%= request.getContextPath() %>/san-pham/chi-tiet?id=<%= sp.getId() %>" class="btn btn-outline-danger">Xem chi tiết</a>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>

        <!-- Pagination -->
        <nav aria-label="Phân trang">
            <ul class="pagination justify-content-center">
                <% if (currentPage > 1) { %>
                    <li class="page-item">
                        <a class="page-link" href="?page=<%= currentPage - 1 %>">«</a>
                    </li>
                <% } else { %>
                    <li class="page-item disabled"><span class="page-link">«</span></li>
                <% } %>

                <% for (int i = 1; i <= totalPages; i++) { %>
                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                        <a class="page-link" href="?page=<%= i %>"><%= i %></a>
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
</main>

<footer class="footer mt-auto">
    <p>&copy; 2025 GirlssShop. All rights reserved.</p>
    <p>Email: lienhe@myphamshop.vn | Hotline: 1900 1234</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>