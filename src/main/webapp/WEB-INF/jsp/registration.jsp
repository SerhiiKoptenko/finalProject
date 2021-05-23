<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Registration page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <link rel="stylesheet" href="static/css/app.css">
</head>

<body id="reg_page">
<header id="head">
    <nav class="navbar navbar-expand-lg navbar-light bg-light bg-gradient">
        <div class="container-fluid">
            <a class="navbar-brand text-center" href="#">University</a>
            <div><a id="reg_button" class="btn btn-outline-primary" href="#">Register</a>
                <a class="btn btn-primary" href="#" role="button">Log in</a></div>
        </div>
    </nav>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Features</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Pricing</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                    </li>
                </ul>
            </div>

        </div>
    </nav>
</header>
        <main>
            <c:set var="regResult" scope="page" value="${pageContext.request.getParameter(\"registrationResult\")}"/>
            <c:set var="prevFirstName" scope="page" value="${pageContext.request.getParameter(\"firstName\")}"/>
            <c:set var="prevLastName" scope="page" value="${pageContext.request.getParameter(\"lastName\")}"/>
            <c:set var="prevLogin" scope="page" value="${pageContext.request.getParameter(\"login\")}"/>
            <c:if test="${\"success\" ne regResult}">
                <!--Registration form-->
                <div class="registrationForm container mx-auto p-5 rounded ">
                    <form action="registration_page" method="post">
                        <input type="hidden" name="command" value="register">
                        <h3 class="text-center">Registration</h3>
                        <div class="mb-3">
                            <label for="firstName" class="form-label ">First name</label>
                            <input type="text" class="form-control" name="firstName" id="firstName" required
                                   placeholder="First name" value="${prevFirstName}">
                        </div>
                        <div class="mb-3">
                            <label for="lastName" class="form-label">Last name</label>
                            <input type="text" class="form-control" name="lastName" id="lastName" required
                                   placeholder="Last name" value="${prevLastName}">
                        </div>
                        <div class="mb-3">
                            <label for="login" class="form-label">Login</label>
                            <input type="text" class="form-control" name="login" id="login" required
                                   placeholder="Login" value="${prevLogin}">
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" name="password" id="password" required
                                   placeholder="Password">
                        </div>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form>
                </div>
                <!--Registration form-->
            </c:if>
            <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
                <symbol id="check-circle-fill" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"></path>
                </symbol>
                <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"></path>
                </symbol>
            </svg>
            <c:choose>
                <c:when test="${\"success\" eq regResult}">
                    <div class="alert alert-success d-flex align-items-center" role="alert">
                        <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Success:"><use xlink:href="#check-circle-fill"/></svg>
                        <div>
                            Registration has been successful! You can proceed to login now.
                        </div>
                    </div>
                </c:when>
                <c:when test="${\"userExists\" eq regResult}">
                    <div class="alert alert-danger d-flex align-items-center" role="alert">
                        <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                        <div>
                            User with login ${prevLogin} already exists. Please try another login.
                        </div>
                    </div>
                </c:when>
                <c:when test="${\"invalidData\" eq regResult}">
                    <div class="alert alert-danger d-flex align-items-center" role="alert">
                        <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                        <div>
                            <c:set var="invalidParameters" scope="page" value="${pageContext.request.getParameterValues(\"invalidParameter\")}"/>
                            <c:set var="contains" value="false" />
                                <c:forEach var="item" items="${invalidParameters}">
                                    <c:if test="${item eq \"firstName\"}">
                                        <c:set var="contains" value="true" />
                                    </c:if>
                                </c:forEach>
                            <c:if test="${contains}">
                                Invalid first name.<br>
                            </c:if>
                            <c:set var="contains" value="false" />
                            <c:forEach var="item" items="${invalidParameters}">
                                <c:if test="${item eq \"lastName\"}">
                                    <c:set var="contains" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${contains}">
                                Invalid last name.<br>
                            </c:if>
                            <c:set var="contains" value="false" />
                            <c:forEach var="item" items="${invalidParameters}">
                                <c:if test="${item eq \"login\"}">
                                    <c:set var="contains" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${contains}">
                                Invalid login.<br>
                            </c:if>
                            <c:set var="contains" value="false" />
                            <c:forEach var="item" items="${invalidParameters}">
                                <c:if test="${item eq \"password\"}">
                                    <c:set var="contains" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${contains}">
                                Invalid password.<br>
                            </c:if>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
</body>

</html>