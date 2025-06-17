<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${sp.id == 0 ? "Thêm sản phẩm mới" : "Cập nhật sản phẩm"}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">${sp.id == 0 ? "Thêm sản phẩm mới" : "Cập nhật sản phẩm"}</h4>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/san-pham" method="post">
                <input type="hidden" name="id" value="${sp.id}" />

                <div class="form-group">
                    <label for="tenSanPham">Tên sản phẩm</label>
                    <input type="text" class="form-control" name="tenSanPham" id="tenSanPham" value="${sp.tenSanPham}" required>
                </div>

			<div class="form-group">
			    <label for="idLoai">Loại sản phẩm</label>
			    <select name="idLoai" id="idLoai" class="form-control" required>
			        <option value="">-- Chọn loại sản phẩm --</option>
			        <c:forEach var="loai" items="${danhSachLoai}">
			            <option value="${loai.id}" <c:if test="${loai.id == sp.idLoai}">selected</c:if>>
			                ${loai.tenLoai}
			            </option>
			        </c:forEach>
			    </select>
			</div>



                <div class="form-group">
                    <label for="moTa">Mô tả</label>
                    <textarea class="form-control" name="moTa" id="moTa" rows="3">${sp.moTa}</textarea>
                </div>

                <div class="form-group">
                    <label for="gia">Giá</label>
                    <input type="number" step="0.01" class="form-control" name="gia" id="gia" value="${sp.gia}" required>
                </div>

                <div class="form-group">
                    <label for="soLuong">Số lượng</label>
                    <input type="number" class="form-control" name="soLuong" id="soLuong" value="${sp.soLuong}" required>
                </div>

                <div class="form-group">
                    <label for="anhUrl">Đường dẫn ảnh (URL)</label>
                    <input type="text" class="form-control" name="anhUrl" id="anhUrl" value="${sp.anhUrl}">
                </div>

                <div class="form-group d-flex justify-content-between">
                    <button type="submit" class="btn btn-success">
                        ${sp.id == 0 ? "Thêm sản phẩm" : "Cập nhật sản phẩm"}
                    </button>
                    <a href="${pageContext.request.contextPath}/san-pham/quan-ly" class="btn btn-secondary">Quay lại</a>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Optional JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
