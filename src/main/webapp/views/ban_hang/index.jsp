<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>--%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bán hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <jsp:include page="../layout/page/taglibViewsAdmin.jsp"></jsp:include>
</head>
<body>
<div class="wrapper">
    <%--Menu--%>
    <jsp:include page="../layout/page/menu.jsp"/>
    <div class="main">
        <%--Header--%>
        <%--        <form action="/ban-hang/tao-hoa-don" method="post">--%>
        <jsp:include page="../layout/page/header.jsp"/>
        <%--        </form>--%>
        <%--Body--%>
        <div class="box container my-2">
            <div class="row">
                <div class="col-7">
                    <h2>Danh sách hoá đơn</h2>
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Tên nhân viên</th>
                            <th scope="col">Tên khách hàng</th>
                            <th scope="col">Ngày mua hàng</th>
                            <th scope="col">Trạng thái</th>
                            <th scope="col">Hành động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listHD}" var="hd">
                            <tr>
                                <td>${hd.id}</td>
                                <td>${hd.idNV.ten}</td>
                                <td>${hd.idKH.ten}</td>
                                <td>${hd.ngayMuaHang}</td>
                                <td>${hd.hienThiTT()}</td>
                                <td>
                                    <a class="btn btn-warning" href="/ban-hang/selectHD/${hd.id}">Chọn</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <h2>Danh sách hoá đơn chi tiết</h2>
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">ID hóa đơn</th>
                            <th scope="col">Mã SPCT</th>
                            <th scope="col">Số lượng</th>
                            <th scope="col">Đơn giá</th>
                            <th scope="col">Trạng thái</th>
                            <th scope="col">Hành động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listHDCT}" var="hdct">
                            <tr>
                                <td>${hdct.id}</td>
                                <td>${hdct.idHD.id}</td>
                                <td>${hdct.idSPCT.id}</td>
                                <td>${hdct.soLuong}</td>
                                <td>${hdct.donGia}</td>
                                <td>${hdct.hienThiTT()}</td>
                                <td>
                                    <a class="btn btn-danger" href="/ban-hang/deleteHDCT/${hdct.id}">Delete</a>
                                        <%--                                    <a class="btn btn-warning" href="/ban-hang/updateHDCT/${hdct.id}">Update</a>--%>
                                    <button type="button" class="btn btn-success" data-bs-toggle="modal"
                                            data-bs-target="#myModal-${hdct.id}">
                                        Update
                                    </button>
                                    <!-- The Modal -->
                                    <div class="modal fade" id="myModal-${hdct.id}" tabindex="-1"
                                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <!-- Modal Header -->
                                                <div class="modal-header">
                                                    <h4 class="modal-title">Mời sửa số lượng</h4>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                </div>
                                                <!-- Modal body -->
                                                <form action="/ban-hang/updateHDCT/${hdct.id}" method="post">
                                                    <div class="modal-body">
                                                        <input type="number" name="soLuong" class="form-control"
                                                               required>
                                                    </div>
                                                    <c:if test="${not empty errorSL}">
                                                        <div class="alert alert-danger">${errorSL}</div>
                                                    </c:if>
                                                    <!-- Modal footer -->
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary"
                                                                data-bs-dismiss="modal">Hủy
                                                        </button>
                                                        <button type="submit" class="btn btn-warning">Nhập</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-5">
                    <form action="/ban-hang/index" method="get">
                        <div class="mb-3">
                            <label class="form-label">Tìm kiếm</label>
                            <input type="text" class="form-control" name="sdtKH" value="${sdtKH}">
                            <c:if test="${not empty errorMessageSDT}">
                                <div class="text text-danger">${errorMessageSDT}</div>
                            </c:if>
                        </div>
                        <div class="mb-3">
                            <button class="btn btn-primary" type="submit">Search</button>
                        </div>
                    </form>
                    <form action="/ban-hang/tao-hoa-don/${loggedInUser.id}" method="post">
                        <h2>Tạo hoá đơn</h2>
                        <div class="row">
                            <div class="mb-3">
                                <label class="form-label">Số điện thoại</label>
                                <input type="text" class="form-control" name="sdt"
                                       value="${listKHDetail.sdt} ${khachHangDetail.sdt}">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Tên khách hàng</label>
                                <input type="text" class="form-control" name="tenKH"
                                       value="${listKHDetail.ten} ${khachHangDetail.ten}">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">ID Hóa đơn</label>
                                <input type="text" readonly class="form-control" name="idHoaDon"
                                       value="${hoaDonDetail.id}">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Tổng tiền</label>
                                <input type="text" class="form-control" readonly name="tongTien"
                                       value="${tongTien}">
                            </div>
                            <div>
                                <button class="btn btn-primary" type="submit">Tạo hoá đơn</button>
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                        data-bs-target="#confirmModal">
                                    Thanh toán
                                </button>
                                <!-- Modal xác nhận -->
                                <div class="modal fade" id="confirmModal" tabindex="-1"
                                     aria-labelledby="confirmModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="confirmModalLabel">Xác nhận thanh toán</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                Bạn có chắc chắn muốn thanh toán hóa đơn với id này không ${hoaDonDetail.id} không?
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                    Hủy
                                                </button>
                                                <button class="btn btn-primary" type="submit"
                                                        formaction="/ban-hang/thanh-toan/${loggedInUser.id}">Thanh toán
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div>
                <h2>Danh sách chi tiết sản phẩm</h2>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Mã</th>
                        <th scope="col">Tên sản phẩm</th>
                        <th scope="col">Tên màu</th>
                        <th scope="col">Tên kích thước</th>
                        <th scope="col">Số lượng</th>
                        <th scope="col">Đơn giá</th>
                        <th scope="col">Trạng thái</th>
                        <th scope="col">Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listSPCT}" var="sp">
                        <tr>
                            <td>${sp.id}</td>
                            <td>${sp.ma}</td>
                            <td>${sp.idSanPham.ten}</td>
                            <td>${sp.idMauSac.ten}</td>
                            <td>${sp.idKichThuoc.ten}</td>
                            <td>${sp.soLuong}</td>
                            <td>${sp.donGia}</td>
                            <td>${sp.hienThiTT()}</td>
                            <td>
                                <button type="button" class="btn btn-success" data-bs-toggle="modal"
                                        data-bs-target="#myModal-${sp.id}">
                                    Chọn mua
                                </button>
                                <!-- The Modal -->
                                <div class="modal" id="myModal-${sp.id}">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <!-- Modal Header -->
                                            <div class="modal-header">
                                                <h4 class="modal-title">Mời nhập số lượng muốn</h4>
                                                <button type="button" class="btn-close"
                                                        data-bs-dismiss="modal"></button>
                                            </div>
                                            <!-- Modal body -->
                                            <form action="/ban-hang/selectSPCT/${sp.id}" method="post">
                                                <div class="modal-body">
                                                    <input type="number" name="soLuong" class="form-control" required>
                                                </div>
                                                <c:if test="${errorSoLuong == false}">
                                                    <div class="alert alert-danger">${errorSoLuong}</div>
                                                </c:if>
                                                <!-- Modal footer -->
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">
                                                        Hủy
                                                    </button>
                                                    <button type="submit" class="btn btn-warning">Nhập</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <%--Footer--%>
        <jsp:include page="../layout/page/footer.jsp"/>
    </div>
</div>
<script>
    const hamBurger = document.querySelector(".toggle-btn");

    hamBurger.addEventListener("click", function () {
        document.querySelector("#sidebar").classList.toggle("expand");
    });
</script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap');

    ::after,
    ::before {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
    }

    a {
        text-decoration: none;
    }

    li {
        list-style: none;
    }

    body {
        font-family: 'Poppins', sans-serif;
    }

    .wrapper {
        display: flex;
    }

    .main {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
        width: 100%;
        overflow: hidden;
        transition: all 0.35s ease-in-out;
        background-color: #fff;
        min-width: 0;
    }

    #sidebar {
        width: 70px;
        min-width: 70px;
        z-index: 1000;
        transition: all .25s ease-in-out;
        background-color: #0e2238;
        display: flex;
        flex-direction: column;
    }

    #sidebar.expand {
        width: 260px;
        min-width: 260px;
    }

    .toggle-btn {
        background-color: transparent;
        cursor: pointer;
        border: 0;
        padding: 1rem 1.5rem;
    }

    .toggle-btn i {
        font-size: 1.5rem;
        color: #FFF;
    }

    .sidebar-logo {
        margin: auto 0;
    }

    .sidebar-logo a {
        color: #FFF;
        font-size: 1.15rem;
        font-weight: 600;
    }

    #sidebar:not(.expand) .sidebar-logo,
    #sidebar:not(.expand) a.sidebar-link span {
        display: none;
    }

    #sidebar.expand .sidebar-logo,
    #sidebar.expand a.sidebar-link span {
        animation: fadeIn .25s ease;
    }

    @keyframes fadeIn {
        0% {
            opacity: 0;
        }

        100% {
            opacity: 1;
        }
    }

    .sidebar-nav {
        padding: 2rem 0;
        flex: 1 1 auto;
    }

    a.sidebar-link {
        padding: .625rem 1.625rem;
        color: #FFF;
        display: block;
        font-size: 0.9rem;
        white-space: nowrap;
        border-left: 3px solid transparent;
    }

    .sidebar-link i,
    .dropdown-item i {
        font-size: 1.1rem;
        margin-right: .75rem;
    }

    a.sidebar-link:hover {
        background-color: rgba(255, 255, 255, .075);
        border-left: 3px solid #3b7ddd;
    }

    .sidebar-item {
        position: relative;
    }

    #sidebar:not(.expand) .sidebar-item .sidebar-dropdown {
        position: absolute;
        top: 0;
        left: 70px;
        background-color: #0e2238;
        padding: 0;
        min-width: 15rem;
        display: none;
    }

    #sidebar:not(.expand) .sidebar-item:hover .has-dropdown + .sidebar-dropdown {
        display: block;
        max-height: 15em;
        width: 100%;
        opacity: 1;
    }

    #sidebar.expand .sidebar-link[data-bs-toggle="collapse"]::after {
        border: solid;
        border-width: 0 .075rem .075rem 0;
        content: "";
        display: inline-block;
        padding: 2px;
        position: absolute;
        right: 1.5rem;
        top: 1.4rem;
        transform: rotate(-135deg);
        transition: all .2s ease-out;
    }

    #sidebar.expand .sidebar-link[data-bs-toggle="collapse"].collapsed::after {
        transform: rotate(45deg);
        transition: all .2s ease-out;
    }

    .navbar {
        background-color: #f5f5f5;
        box-shadow: 0 0 2rem 0 rgba(33, 37, 41, .1);
    }

    .navbar-expand .navbar-collapse {
        min-width: 200px;
    }

    .avatar {
        height: 40px;
        width: 40px;
    }

    @media (min-width: 768px) {
    }
</style>
</body>
</html>



