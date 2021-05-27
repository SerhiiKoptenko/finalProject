<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta charset="UTF-8">
    <title>${pageTitle}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <link rel="stylesheet" href="static/css/app.css">

</head>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light bg-gradient">
        <div class="container-fluid">
            <a class="navbar-brand text-center" href="#">University</a>
            <div>
            <c:choose>
                <c:when test="${sessionScope.get(\"userRole\") eq \"GUEST\"}">
                    <a id="reg_button" class="btn btn-outline-primary" href="registration_page">Register</a>
                        <a id="sign_in_button" class="btn btn-primary" href="sign_in_page" role="button">Sign in</a>
                </c:when>
            <c:otherwise>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                </svg>
                <a href="#">${userLogin}</a>
                <a id="sign_out_button" class="btn btn-danger" href="signOut" role="button">Sign out</a>
            </c:otherwise>
            </c:choose>
            </div>
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
                    <c:if test="${sessionScope.get(\"userRole\") eq \"ADMIN\"}">
                        <li class="nav-item">
                            <a class="nav-link" href="admin/admin_basis">Admin page</a>
                        </li>
                    </c:if>
                </ul>
            </div>

        </div>
    </nav>
</header>