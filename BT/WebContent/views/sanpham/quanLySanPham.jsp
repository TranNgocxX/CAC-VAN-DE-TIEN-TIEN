<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="BTL.models.SanPham" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách sản phẩm</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@400;600&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Quicksand', sans-serif;
            background-color: #white;
        }
        th, td {
		    white-space: nowrap;            
		    vertical-align: middle;         
		}
		
		table {
		    table-layout: auto !important;  
		    width: 100%;
		}
		
		td .btn {
		    white-space: nowrap;
		}
	    td:nth-child(3) {
		    max-width: 250px;
		    white-space: normal;           
		    word-wrap: break-word;
		}
	    

        h2 {
            color: #c2185b;
            font-weight: 600;
        }

        .btn-success {
            background-color: #f06292;
            border: none;
        }

        .btn-success:hover {
            background-color: #ec407a;
        }

        .btn-warning {
            background-color: #fdd835;
            border: none;
        }

        .btn-danger {
            background-color: #e57373;
            border: none;
        }

        .table thead {
            background-color: #f8bbd0;
        }

        .table-hover tbody tr:hover {
            background-color: #fce4ec;
            transition: background-color 0.3s ease;
        }

        .fade-in {
            opacity: 0;
            transition: opacity 0.6s ease;
        }

        .fade-in.show {
            opacity: 1;
        }

        .pagination {
            justify-content: center;
        }

        .pagination .page-item.active .page-link {
            background-color: #f06292;
            border-color: #f06292;
        }

        .page-link {
            color: #f06292;
        }

        .page-link:hover {
            background-color: #fce4ec;
        }

        /* Responsive table layout improvement */
        .table-responsive {
            overflow-x: auto;
        }

        /* Optional: max-width container for large screen */
        .container-xl {
            max-width: 1400px;
        }
    </style>
</head>
<body>

<div class="container-xl mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Danh sách sản phẩm</h2>
        <a href="${pageContext.request.contextPath}/san-pham/them" class="btn btn-success">+ Thêm sản phẩm</a>
    </div>

    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle fade-in" id="productTable">
            <thead class="text-center">
                <tr>
                    <th style="">ID</th>
                    <th style="">Tên sản phẩm</th>
                    <th style="">Mô tả</th>
                    <th style="">Giá</th>
                    <th style="">Số lượng</th>
                    <th style="">Ảnh</th>
                    <th style="">Ngày tạo</th>
                    <th style="">Ngày cập nhật</th>
                    <th style="">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="sp" items="${danhSachSanPham}">
                    <tr>
                        <td class="text-center">${sp.id}</td>
                        <td>${sp.tenSanPham}</td>
                        <td>${sp.moTa}</td>
                        <td class="text-right">
                            <fmt:formatNumber value="${sp.gia}" type="currency" currencySymbol="₫"/>
                        </td>
                        <td class="text-center">${sp.soLuong}</td>                        
                        <td class="text-center">
							<img src="${pageContext.request.contextPath}/images/${sp.anhUrl}" 
							     alt="ảnh sản phẩm"
							     width="60" height="60"
							     style="object-fit: cover; border-radius: 5px;" />
                        </td>
                        <td><fmt:formatDate value="${sp.ngayTao}" pattern="dd/MM/yyyy HH:mm" /></td>
                        <td><fmt:formatDate value="${sp.ngayCapNhat}" pattern="dd/MM/yyyy HH:mm" /></td>
                        <td class="text-center">
                            <a href="${pageContext.request.contextPath}/san-pham/sua?id=${sp.id}" class="btn btn-sm btn-warning">Sửa</a>
                            <a href="${pageContext.request.contextPath}/san-pham/xoa?id=${sp.id}" class="btn btn-sm btn-danger"
                               onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Phân trang mẫu -->
    <nav aria-label="Phân trang sản phẩm">
        <ul class="pagination mt-4">
            <li class="page-item disabled"><span class="page-link">«</span></li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item"><a class="page-link" href="#">»</a></li>
        </ul>
    </nav>
</div>

<script>
    window.onload = function () {
        document.getElementById('productTable').classList.add('show');
    };
</script>

<!-- JS Bootstrap -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
