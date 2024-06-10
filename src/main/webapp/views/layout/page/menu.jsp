<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<aside id="sidebar">
    <div class="d-flex">
        <button class="toggle-btn" type="button">
            <i class="lni lni-java"></i>
        </button>
        <div class="sidebar-logo">
            <a href="/admin/trang-chu">The Habits Shop</a>
        </div>
    </div>
    <ul class="sidebar-nav">
        <li class="sidebar-item">
            <a href="/ban-hang/index" class="sidebar-link">
                <i class="fa-solid fa-truck-fast"></i>
                <span>Bán Hàng</span>
            </a>
        </li>
        <c:if test="${sessionScope.role == 'admin'}">
            <li class="sidebar-item">
                <a href="/hoa-don/index" class="sidebar-link collapsed has-dropdown" data-bs-toggle="collapse"
                   data-bs-target="#auth" aria-expanded="false" aria-controls="auth">
                    <i class="fa-solid fa-clipboard"></i>
                    <span>Quản Lý Đơn Hàng</span>
                </a>
                <ul id="auth" class="sidebar-dropdown list-unstyled collapse" data-bs-parent="#sidebar">
                    <li class="sidebar-item">
                        <a href="/hoa-don/index" class="sidebar-link">Quản Lý Hóa Đơn</a>
                    </li>
                    <li class="sidebar-item">
                        <a href="/hdct/index" class="sidebar-link">Quản Lý Hóa Đơn Chi Tiết</a>
                    </li>
                </ul>
            </li>
        </c:if>
        <li class="sidebar-item">
            <a href="/spct/index" class="sidebar-link collapsed has-dropdown" data-bs-toggle="collapse"
               data-bs-target="#multi" aria-expanded="false" aria-controls="multi">
                <i class="fa-solid fa-folder-open"></i>
                <span>Quản Lý Sản Phẩm</span>
            </a>
            <ul id="multi" class="sidebar-dropdown list-unstyled collapse" data-bs-parent="#sidebar">
                <li class="sidebar-item">
                    <a href="#" class="sidebar-link collapsed" data-bs-toggle="collapse"
                       data-bs-target="#multi-two" aria-expanded="false" aria-controls="multi-two">
                        Chung
                    </a>
                    <ul id="multi-two" class="sidebar-dropdown list-unstyled collapse">
                        <li class="sidebar-item">
                            <a href="/spct/index" class="sidebar-link"><i class="fa-light fa-shoe-prints"></i> Quản Lý
                                CTSP</a>
                        </li>
                        <li class="sidebar-item">
                            <a href="/san-pham/index" class="sidebar-link"><i class="fa-light fa-shoe-prints"></i> Quản
                                Lý Sản Phẩm</a>
                        </li>
                        <li class="sidebar-item">
                            <a href="/mau-sac/index" class="sidebar-link"><i class="fa-light fa-dog"></i> Quản Lý Màu
                                Sắc</a>
                        </li>
                        <li class="sidebar-item">
                            <a href="/kich-thuoc/index" class="sidebar-link"><i class="fa-light fa-hippo"></i> Quản Lý
                                Kích Cỡ</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>
        <li class="sidebar-item">
            <a href="/nhan-vien/index" class="sidebar-link">
                <i class="fa-solid fa-user"></i>
                <span>Quản Lý Nhân Viên</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="/khach-hang/index" class="sidebar-link">
                <i class="fa-solid fa-user"></i>
                <span>Quản Lý Khách Hàng</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="#" class="sidebar-link">
                <i class="lni lni-cog"></i>
                <span>Setting</span>
            </a>
        </li>
    </ul>
    <div class="sidebar-footer">
        <a href="/login" class="sidebar-link">
            <i class="lni lni-exit"></i>
            <span>Logout</span>
        </a>
    </div>
</aside>
