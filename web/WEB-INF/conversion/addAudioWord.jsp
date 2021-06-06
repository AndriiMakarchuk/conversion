
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
                    <a href="/conversion">
                        <img  alt="Conversion Logo">
                    </a>
                </li>
                <li>
                    <a href="/conversion">Conversion</a>
                </li>
                <li>
                    <a href="/audioWordList?name=personal">My Dictionary</a>
                </li>
                <li>
                    <a href="/conversionResult">Conversion Result</a>
                </li>
                <c:if test="${sessionScope.user.profile.developerName == 'Administrator'}">
                    <li>
                        <a href="/audioWordList?name=standard">Standard Dictionary</a>
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
                                        <c:if test="${param.get('type') != 'update'}">
                                            <label for="wordName" class="form-label">Word Name</label>
                                            <input name="wordName" type="text" class="form-control mb-3" id="wordName" required>
                                            <c:if test="${errorMessage != null}">
                                                <p class="text-danger mt-3">${errorMessage}</p>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${param.get('type') == 'update'}">
                                            <label for="wordName" class="form-label">Update "${param.get("wordName")}" Word</label>
<%--                                            <input name="wordName" type="hidden" class="form-control mb-3" id="wordName" value="${wordName}" required>--%>
                                        </c:if>
                                    </div>
                                    <div class="form-group">
                                        <label for="file" class="form-label">Word Audio File(mp3)</label>
                                        <input name="wordAudioData" accept=".mp3" type="file" class="form-control mb-3" id="file" required>
                                    </div>
                                        <button type="submit" name="action" class="btn pull-right" value="Save" style="margin-top: 10px">Save</button>
                                        <c:if test="${param.get('type') != 'update'}">
                                            <button type="submit" name="action" class="btn pull-right" value="Save & New" style="margin-top: 10px">Save & New</button>
                                        </c:if>
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