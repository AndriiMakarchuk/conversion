
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<html>--%>
<%--<head>--%>
<%--    <meta charset="utf-8">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1">--%>

<%--    <link rel="stylesheet" href="style/styleRegistration.css">--%>
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"--%>
<%--          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">--%>
<%--    <title>Conversion</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--    <form style="width: 400px; margin: auto; padding: 30px;" method="post" enctype="multipart/form-data">--%>
<%--        <label class="form-label">Word Name</label>--%>
<%--        <input name="wordName" type="text" class="form-control mb-3" placeholder="Word name" required>--%>
<%--        <c:if test="${errorMessage != null}">--%>
<%--            <p class="text-danger mt-3">${errorMessage}</p>--%>
<%--        </c:if>--%>
<%--        <br/>--%>
<%--        <label class="form-label">Word Audio File</label>--%>
<%--        <input name="wordAudioData" accept=".mp3" type="file" class="form-control mb-3" placeholder="Word file" required>--%>
<%--        <div>--%>
<%--            <input type="submit" name="action" class="btn btn-sm btn-dark" value="Save" style="width: 270px;">--%>
<%--            <input type="submit" name="action" class="btn btn-sm btn-dark" value="Save & New" style="width: 270px;">--%>
<%--        </div>--%>
<%--    </form>--%>
<%--    <form style="width: 400px; margin: auto; padding: 30px;" method="post">--%>
<%--        <input type="submit" name="action" class="btn btn-sm btn-dark" value="Cancel" style="width: 270px;">--%>
<%--    </form>--%>
<%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"--%>
<%--            integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"--%>
<%--            crossorigin="anonymous"></script>--%>
<%--</body>--%>
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
                                <form method="post" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="wordName" class="form-label">Word Name</label>
                                        <input name="wordName" type="text" class="form-control mb-3" id="wordName" required>
                                        <c:if test="${errorMessage != null}">
                                            <p class="text-danger mt-3">${errorMessage}</p>
                                        </c:if>
                                    </div>
                                    <div class="form-group">
                                        <label for="file" class="form-label">Word Audio File(mp3)</label>
                                        <input name="wordAudioData" accept=".mp3" type="file" class="form-control mb-3" id="file" required>
                                    </div>
<%--                                    <label class="form-label">Word Audio File</label>--%>
<%--                                    <input name="wordAudioData" accept=".mp3" type="file" class="form-control mb-3" placeholder="Word file" required>--%>

                                        <button type="submit" name="action" class="btn pull-right" value="Save" style="margin-top: 10px">Save</button>
                                        <button type="submit" name="action" class="btn pull-right" value="Save & New" style="margin-top: 10px">Save & New</button>
                                </form>
                                <form  method="post">
                                    <button type="submit" name="action" class="btn pull-right" value="Cancel" style="margin-top: 10px">Cancel</button>
                                    <%--                                    <input type="submit" name="action" class="btn btn-sm btn-dark" value="Cancel" style="width: 270px;">--%>
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