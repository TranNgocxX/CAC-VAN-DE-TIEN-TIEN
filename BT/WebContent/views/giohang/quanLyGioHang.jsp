<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Chi tiết giỏ hàng</title>

  <!-- Bootstrap & Bootstrap Icons -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />

  <style>
    body {
      background-color: #fff0f5;
      font-family: 'Quicksand', sans-serif;
    }

    .wrapper {
      max-width: 1200px;
      margin: 60px auto;
      background: #ffffff;
      padding: 40px;
      border-radius: 12px;
      box-shadow: 0 8px 20px rgba(0,0,0,0.06);
    }

    h2.title {
      text-align: center;
      color: #d81b60;
      font-weight: bold;
      margin-bottom: 30px;
      letter-spacing: 1px;
      text-transform: uppercase;
    }

    .table th {
      background-color: #f8bbd0;
      color: #6a1b9a;
      vertical-align: middle;
    }

    .table td {
      vertical-align: middle;
      background-color: #fffdfd;
    }

    .btn-outline-pink {
      color: #d81b60;
      border-color: #d81b60;
    }

    .btn-outline-pink:hover {
      background-color: #d81b60;
      color: white;
    }

    select[disabled] {
      background-color: #f9f9f9;
      opacity: 1;
    }
  </style>
</head>
<body>

<div class="wrapper">
  <h2 class="title">QUẢN LÝ CHI TIẾT GIỎ HÀNG</h2>

  <table class="table table-bordered text-center align-middle">
    <thead>
      <tr>
        <th>Người dùng</th>
        <th>Sản phẩm</th>
        <th>Loại</th>
        <th>Số lượng</th>
        <th>Đơn giá</th>
        <th>Tổng tiền</th>
        <th>Ngày thêm</th>
        <th>Trạng thái</th>
        <th>Hành động</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="ct" items="${danhSachChiTiet}">
        <tr id="row-${ct.idChiTiet}">
          <td>${ct.tenNguoiDung}</td>
          <td class="text-start ps-3">${ct.tenSanPham}</td>
          <td>${ct.tenLoai}</td>
          <td>${ct.soLuong}</td>
          <td>₫${ct.gia}</td>
          <td>₫${ct.tongTien}</td>
          <td>${ct.ngayThem}</td>
          <td>
            <form method="post" action="${pageContext.request.contextPath}/quan-ly-gio-hang"
                  id="form-${ct.idChiTiet}" class="d-flex align-items-center justify-content-center gap-2">
              <input type="hidden" name="action" value="cap-nhat-trang-thai" />
              <input type="hidden" name="idGioHangChiTiet" value="${ct.idChiTiet}" />
              
<select name="trangThaiMoi" id="trangThai-${ct.idChiTiet}"
        class="form-select form-select-sm w-auto" disabled>
  <option value="dang_mua" ${ct.trangThai == 'dang_mua' ? 'selected' : ''}>Đang mua</option>
  <option value="da_thanh_toan" ${ct.trangThai == 'da_thanh_toan' ? 'selected' : ''}>Đã thanh toán</option>
</select>


              <div class="d-none" id="group-${ct.idChiTiet}">
                <button type="submit" class="btn btn-sm btn-success" title="Lưu">
                  <i class="bi bi-check-lg"></i>
                </button>
                <button type="button" class="btn btn-sm btn-secondary btn-huy" data-id="${ct.idChiTiet}" title="Hủy">
                  <i class="bi bi-x-lg"></i>
                </button>
              </div>
            </form>
          </td>
          <td class="text-nowrap">
            <button type="button" class="btn btn-sm btn-outline-pink btn-sua" data-id="${ct.idChiTiet}" title="Sửa">
              <i class="bi bi-pencil-square"></i>
            </button>

            <form method="post" action="${pageContext.request.contextPath}/quan-ly-gio-hang"
                  class="d-inline" onsubmit="return confirm('Bạn chắc chắn muốn xoá chi tiết này?');">
              <input type="hidden" name="action" value="xoa-chi-tiet" />
              <input type="hidden" name="idGioHangChiTiet" value="${ct.idChiTiet}" />
              <button type="submit" class="btn btn-sm btn-outline-danger" title="Xoá">
                <i class="bi bi-trash3"></i>
              </button>
            </form>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<script>
  document.querySelectorAll('.btn-sua').forEach(btn => {
    btn.addEventListener('click', () => {
      const id = btn.dataset.id;
      const select = document.getElementById(`trangThai-${id}`);
      const group = document.getElementById(`group-${id}`);

      select.disabled = false;
      group.classList.remove('d-none');
      btn.classList.add('d-none');
    });
  });

  document.querySelectorAll('.btn-sua').forEach(btn => {
	  btn.addEventListener('click', () => {
	    const id = btn.dataset.id;
	    const select = document.getElementById(`trangThai-${id}`);
	    const input = document.getElementById(`soLuong-${id}`);
	    const group = document.getElementById(`group-${id}`);

	    select.disabled = false;
	    input.disabled = false;
	    group.classList.remove('d-none');
	    btn.classList.add('d-none');
	  });
	});

	document.querySelectorAll('.btn-huy').forEach(btn => {
	  btn.addEventListener('click', () => {
	    const id = btn.dataset.id;
	    const select = document.getElementById(`trangThai-${id}`);
	    const input = document.getElementById(`soLuong-${id}`);
	    const group = document.getElementById(`group-${id}`);
	    const suaBtn = document.querySelector(`.btn-sua[data-id="${id}"]`);

	    select.disabled = true;
	    input.disabled = true;
	    group.classList.add('d-none');
	    suaBtn.classList.remove('d-none');

	    // Reset về giá trị ban đầu (cần lưu trữ value ban đầu nếu muốn)
	    const selectedOption = select.querySelector('option[selected]');
	    if (selectedOption) {
	      select.value = selectedOption.value;
	    }
	    input.value = input.defaultValue;
	  });
	});

</script>

</body>
</html>
