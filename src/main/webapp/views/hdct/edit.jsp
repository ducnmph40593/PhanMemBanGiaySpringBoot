<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hóa đơn chi tiết</title>
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
    <jsp:include page="../layout/page/menu.jsp"></jsp:include>
    <div class="main">
        <%--Header--%>
        <jsp:include page="../layout/page/header.jsp"></jsp:include>
        <%--Body--%>
            <div class="box container my-2">
                <h2>Sửa hóa đơn chi tiết</h2>
                <form action="/hdct/update/${listHDCTDetail.id}" method="post">
                    <div class="my-3">
                        <label class="form-label">ID hóa đơn</label>
                        <select name="idHD" class="form-select me-2">
                            <c:forEach var="i" items="${listHD}" >
                                <option value="${i.id}"
                                        <c:if test="${i.id == listHDCTDetail.idHD.id}">selected</c:if>>${i.id}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="my-3">
                        <label class="form-label">Mã sản phẩm chi tiết</label>
                        <select name="idSPCT" class="form-select me-2">
                            <c:forEach var="i" items="${listSPCT}">
                                <option value="${i.id}" <c:if test="${i.id == listHDCTDetail.idSPCT.id}">selected</c:if>>
                                        ${i.ma}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="my-3">
                        <label class="form-label">Số lượng</label>
                        <input class="form-control" type="number" name="soLuong" value="${listHDCTDetail.soLuong}">
                        <c:if test="${not empty errors['soLuong']}">
                            <div class="alert alert-danger alert-sm mt-2">${errors['soLuong']}</div>
                        </c:if>
                    </div>
                    <div class="my-3">
                        <label class="form-label">Đơn giá</label>
                        <input class="form-control" type="number" name="donGia" value="${listHDCTDetail.donGia}">
                        <c:if test="${not empty errors['donGia']}">
                            <div class="alert alert-danger alert-sm mt-2">${errors['donGia']}</div>
                        </c:if>
                    </div>
                    <div class="my-3">
                        <label class="form-label">Trạng thái</label>
                        <select id="" class="form-select" name="trangThai">
                            <option value="0" ${listHDCTDetail.trangThai == 0 ? "selected" : ""}>Hoạt động</option>
                            <option value="1" ${listHDCTDetail.trangThai == 1 ? "selected" : ""}>Ngừng hoạt động</option>
                        </select>
                    </div>
                    <div class="my-3">
                        <button class="btn btn-warning" type="submit">Sửa</button>
                        <a class="btn btn-light" href="/hdct/index">Quay lại</a>
                    </div>
                </form>
            </div>
        <%--Footer--%>
        <jsp:include page="../layout/page/footer.jsp"></jsp:include>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
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



