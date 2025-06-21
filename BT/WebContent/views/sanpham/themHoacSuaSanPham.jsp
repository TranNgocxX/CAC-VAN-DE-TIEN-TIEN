<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${sp.id == 0 ? 'Thêm sản phẩm mới' : 'Cập nhật sản phẩm'}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@400;600&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Quicksand', sans-serif;
            background-color: #fff0f5;
        }

        .card-header {
            background-color: #f06292 !important;
            color: white;
            font-weight: bold;
        }

        .form-group label {
            font-weight: 600;
        }

        .error-message {
            color: red;
            font-weight: 600;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="card shadow">
        <div class="card-header">
            <h4 class="mb-0">${sp.id == 0 ? 'Thêm sản phẩm mới' : 'Cập nhật sản phẩm'}</h4>
        </div>
        <div class="card-body">
            <c:if test="${not empty errorMessage}">
                <div class="error-message">${errorMessage}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/san-pham" method="post" accept-charset="UTF-8">
                <input type="hidden" name="id" value="${sp.id}"/>

                <div class="form-group">
                    <label for="tenSanPham">Tên sản phẩm</label>
                    <input type="text" class="form-control" name="tenSanPham" id="tenSanPham"
                           value="${sp.tenSanPham}" required>
                </div>

                <div class="form-group">
                    <label for="idLoai">Loại sản phẩm</label>
                    <select name="idLoai" id="idLoai" class="form-control" required>
                        <option value="">-- Chọn loại sản phẩm --</option>
                        <c:forEach var="loai" items="${danhSachLoai}">
                            <option value="${loai.id}" ${loai.id == sp.idLoai ? 'selected' : ''}>
                                ${loai.tenLoai}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="moTa">Mô tả</label>
                    <textarea class="form-control" name="moTa" id="moTa" rows="3"
                              required>${sp.moTa}</textarea>
                </div>

                <div class="form-group">
                    <label for="gia">Giá</label>
                    <input type="number" step="0.01" class="form-control" name="gia" id="gia"
                           value="${sp.gia > 0 ? sp.gia : ''}" min="1" required>
                </div>

                <div class="form-group">
                    <label for="soLuong">Số lượng</label>
                    <input type="number" class="form-control" name="soLuong" id="soLuong"
                           value="${sp.soLuong > 0 ? sp.soLuong : ''}" min="1" required>
                </div>

                <div class="form-group">
                    <label for="anhUrl">Đường dẫn ảnh (URL)</label>
                    <input type="text" class="form-control" name="anhUrl" id="anhUrl"
                           value="${sp.anhUrl}" required>
                </div>

                <div class="form-group">
                    <label for="phanTramGiam">Phần trăm giảm giá (nếu có)</label>
                    <input type="number" step="0.1" class="form-control" name="phanTramGiam" id="phanTramGiam"
                           value="${sp.id == 0 ? '0' : ''}" min="0" max="100">
                </div>

                <div class="form-group d-flex justify-content-between mt-4">
                    <button type="submit"
                            style="background-color: #ec407a; color: white; border: none; padding: 8px 20px; border-radius: 4px;">
                        ${sp.id == 0 ? 'Thêm sản phẩm' : 'Cập nhật sản phẩm'}
                    </button>
                    <a href="${pageContext.request.contextPath}/san-pham/quan-ly"
                       style="background-color: #ba68c8; color: white; border: none; padding: 8px 20px; border-radius: 4px; text-decoration: none; display: inline-block;">
                        Quay lại
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
