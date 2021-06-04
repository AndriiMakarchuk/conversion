<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<html>--%>
<%--    <head>--%>
<%--        <meta charset="utf-8">--%>
<%--        <meta name="viewport" content="width=device-width, initial-scale=1">--%>

<%--&lt;%&ndash;        <link rel="stylesheet" href="style/styleRegistration.css">&ndash;%&gt;--%>
<%--        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"--%>
<%--              integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">--%>
<%--        <title>Conversion</title>--%>
<%--    </head>--%>
<%--    <body>--%>
<%--    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">--%>
<%--        <a class="navbar-brand" href="#">Navbar</a>--%>
<%--        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">--%>
<%--            <span class="navbar-toggler-icon"></span>--%>
<%--        </button>--%>

<%--        <div class="collapse navbar-collapse" id="navbarSupportedContent">--%>
<%--            <ul class="navbar-nav mr-auto">--%>
<%--                <li class="nav-item active">--%>
<%--                    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>--%>
<%--                </li>--%>
<%--                <li class="nav-item">--%>
<%--                    <a class="nav-link" href="#">Link</a>--%>
<%--                </li>--%>
<%--                <li class="nav-item dropdown">--%>
<%--                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
<%--                        Dropdown--%>
<%--                    </a>--%>
<%--                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">--%>
<%--                        <a class="dropdown-item" href="#">Action</a>--%>
<%--                        <a class="dropdown-item" href="#">Another action</a>--%>
<%--                        <div class="dropdown-divider"></div>--%>
<%--                        <a class="dropdown-item" href="#">Something else here</a>--%>
<%--                    </div>--%>
<%--                </li>--%>
<%--            </ul>--%>
<%--            <form class="form-inline my-2 my-lg-0">--%>
<%--                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">--%>
<%--                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>--%>
<%--            </form>--%>
<%--        </div>--%>
<%--    </nav>--%>
<%--    <form style="width: 400px; margin: auto; padding: 30px;" method="post" enctype="multipart/form-data">--%>
<%--        <label class="form-label">File</label>--%>
<%--        <input name="conversionFile" type="file" accept=".txt" class="form-control mb-3" required>--%>
<%--        <div>--%>
<%--            <input type="submit" name="action" class="btn btn-sm btn-dark" value="conversion" style="width: 270px;">--%>
<%--        </div>--%>
<%--    </form>--%>
<%--    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>--%>
<%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>--%>
<%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"--%>
<%--            integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"--%>
<%--            crossorigin="anonymous">--%>
<%--    </script>--%>
<%--    </body>--%>
<%--</html>--%>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Conversion</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,700,600,800" rel="stylesheet" type="text/css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<%--    <link rel="stylesheet" href="styles/bootstrap.min.css">--%>
<%--    <link rel="stylesheet" href="styles/icomoon-social.css">--%>


    <link rel="stylesheet" href="styles/main.css">
</head>
<body>

<div class="mainmenu-wrapper">
    <div class="container">
        <div class="menuextras">
            <div class="extras">
                <ul>
                    <li><p><c:out value="${sessionScope.user.login}"/></p></li>
                    <li><a href="/logout">Logout</a></li>
                </ul>
            </div>
        </div>
        <nav id="mainmenu" class="mainmenu">
            <ul>
                <li class="logo-wrapper">
                    <a href="index.html">
                        <img  alt="Conversion Logo">
                    </a>
                </li>
                <li>
                    <a href="/conversion">Conversion</a>
                </li>
                <li>
                    <a href="/conversionResult">Conversion Result</a>
                </li>
                <c:if test="${sessionScope.user.profile.developerName == 'Administrator'}">
                    <li>
                        <a href="/audioWordList">Word List</a>
                    </li>
                    <li>
                        <a href="/addAudioWord">Add Word</a>
                    </li>
                </c:if>
<%--                <a href="/logout" style="float: right;max-height: 40px; margin-top: 10px" class="btn pull-right text-right">Logout</a>--%>
<%--                <p style="float: right;max-height: 40px; margin-top: 20px; padding-right: 10px">Andri Makarchuk</p>--%>
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
                                <form role="form" method="post" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="file">TXT File</label>
                                        <input type="file" name="conversionFile" accept=".txt" class="form-control" id="file" placeholder="" required>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" name="action" class="btn pull-right" value="conversion" style="margin-top: 20px">Convert</button>
<%--                                        <a href="/registration" class="btn pull-left">Create account</a>--%>
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
