<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Conversion</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <link rel="stylesheet" href="styles/bootstrap.min.css">
<%--    <link rel="stylesheet" href="styles/icomoon-social.css">--%>
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,700,600,800" rel="stylesheet" type="text/css">

    <link rel="stylesheet" href="styles1/main.css">
</head>
<body>

<div class="mainmenu-wrapper">
    <div class="container">
        <nav id="mainmenu" class="mainmenu">
            <ul>
                <li class="logo-wrapper">
                    <a href="/conversion">
                        <img  alt="Conversion Logo">
                    </a>
                </li>
                <li>
                    <a href="/conversion">Conversion</a>
                </li>
            </ul>
        </nav>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div class="error-page-wrapper">
                    <div class="container text-center">
                        <div class="col-sm-12">
                            <div class="basic-login">
                                <form role="form" method="post">
                                    <div class="form-group">
                                        <label for="login-username"><b>Username</b></label>
                                        <input type="login" name="login" class="form-control" id="login-username" type="text" placeholder="" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="login-password"><b>Password</b></label>
                                        <input type="password" name="password" class="form-control" id="login-password" type="password" placeholder="" required>
                                    </div>
                                    <c:if test="${errorMessage != null}">
                                        <div class="form-group">
                                            <p class="text-danger mt-3">${errorMessage}</p>
                                        </div>
                                    </c:if>
                                    <div class="form-group" >
                                        <button type="submit" class="btn pull-right" value="login">Login</button>
                                        <a href="/registration" class="btn pull-left">Create account</a>
                                        <div class="clearfix"></div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<div class="footer">
    <div class="container">
        <div class="row">
            <div class="col-footer col-md-4 col-xs-6">
                <h3>Contacts</h3>
                <p class="contact-us-details">
                    <b>Address:</b> 4 Tuktora Street, Lviv, Ukraine<br>
                    <b>Phone:</b> +38 068 6531055<br>
                    <b>Fax:</b> +38 068 6531055<br>
                    <b>Email:</b> <a href="mailto:andrii.makarchuk4@gmail.com">andrii.makarchuk4@gmail.com</a>
                </p>
            </div>

        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="footer-copyright">© 2021 Conversion. All rights reserved.</div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
