<%@page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white text-center">
                    <h4>Login</h4>
                </div>
                <div class="card-body">
                    <form action="/login" method="post">
                        <div class="form-group my-3">
                            <label for="email">User:</label>
                            <input id="email" class="form-control" type="text" name="tenDN" value="${data.tenDN}"
                                   placeholder="Enter your user" required>
                        </div>
                        <div class="form-group my-3">
                            <label for="password">Password:</label>
                            <input id="password" class="form-control" type="password" name="matKhau"
                                   value="${data.matKhau}"
                                   placeholder="Enter your password" required>
                            <div class="my-4">
                                <c:if test="${not empty successMessage}">
                                    <span class="alert alert-success">${successMessage}</span>
                                </c:if>
                                <c:if test="${not empty errorMessage}">
                                    <span class="alert alert-danger">${errorMessage}</span>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group text-center my-1">
                            <button class="btn btn-primary btn-block" type="submit">Login</button>
                        </div>
                    </form>
                </div>
                <div class="card-footer text-center">
                    <small>&copy; 2024 Your Company</small>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
