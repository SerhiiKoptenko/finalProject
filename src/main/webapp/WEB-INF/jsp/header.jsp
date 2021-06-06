<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cust" uri="/WEB-INF/tags.tld" %>
<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="i18n.app"/>
<fmt:message key="home" var="home"></fmt:message>
<fmt:message key="register" var="register"/>
<fmt:message key="sign_in" var="sign_in"/>
<fmt:message key="sign_out" var="sign_out"/>
<fmt:message key="manage_courses" var="manage_courses"/>
<fmt:message key="manage_students" var="manage_students"/>
<fmt:message key="register_tutors" var="register_tutors"/>
<fmt:message key="personal_cabinet" var="personal_cabinet"/>
<fmt:message key="user_name" var="user_name"/>
<fmt:message key="submit" var="submit"/>
<fmt:message key="login" var="login"/>
<fmt:message key="password" var="password"/>
<fmt:message key="invalid_login" var="invalid_login"/>
<fmt:message key="invalid_password" var="invalid_password"/>
<fmt:message key="last_name" var="last_name"/>
<fmt:message key="first_name" var="first_name"/>
<fmt:message key="course_name" var="course_name"/>
<fmt:message key="theme_name" var="theme_name"/>
<fmt:message key="start_date" var="start_date"/>
<fmt:message key="end_date" var="end_date"/>
<fmt:message key="description" var="description"/>
<fmt:message key="tutor_first_name" var="tutor_first_name"/>
<fmt:message key="tutor_last_name" var="tutor_last_name"/>
<fmt:message key="students_enrolled" var="students_enrolled"/>
<fmt:message key="action" var="action"/>
<fmt:message key="course_word" var="course_word"/>
<fmt:message key="yes" var="yes"/>
<fmt:message key="no" var="no"/>
<fmt:message key="previous" var="previous"/>
<fmt:message key="next" var="next"/>
<head>
    <meta charset="UTF-8">
    <title>${pageTitle}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">

</head>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light bg-gradient">
        <div class="container-fluid">
            <a class="navbar-brand text-center" href="${pageContext.request.contextPath}/main_page">MyUniversity</a>
            <div>
                <c:choose>
                    <c:when test="${user.role eq \"GUEST\"}">
                        <a id="reg_button" class="btn btn-outline-primary"
                           href="${pageContext.request.contextPath}/registration_page">${register}</a>
                        <a id="sign_in_button" class="btn btn-primary"
                           href="${pageContext.request.contextPath}/sign_in_page" role="button">${sign_in}</a>
                    </c:when>
                    <c:otherwise>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                             class="bi bi-person-circle" viewBox="0 0 16 16">
                            <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                            <path fill-rule="evenodd"
                                  d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                        </svg>
                        <a href="#">${user.login}</a>
                        <a id="sign_out_button" class="btn btn-danger" href="${pageContext.request.contextPath}/signOut"
                           role="button">${sign_out}</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item display-inline">
                        <a class="nav-link active" aria-current="page"
                           href="${pageContext.request.contextPath}">${home}</a>
                    </li>
                    <c:if test="${user.role eq \"ADMIN\"}">
                        <li class="nav-item"><a class="nav-link"
                                                href="${pageContext.request.contextPath}/admin/admin_basis">Admin
                            home</a></li>
                        <li class="nav-item"><a class="nav-link"
                                                href="${pageContext.request.contextPath}/admin/manage_courses">${manage_courses}</a>
                        </li>
                        <li class="nav-item"><a class="nav-link"
                                                href="${pageContext.request.contextPath}/admin/manage_students">${manage_students}</a>
                        </li>
                        <li class="nav-item"><a class="nav-link"
                                                href="${pageContext.request.contextPath}/admin/register_tutor">${register_tutors}</a>
                        </li>
                    </c:if>
                    </li>
                    <c:if test="${user.role eq \"STUDENT\" || user.role eq \"TUTOR\"}">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/user/personal_cabinet">${personal_cabinet}</a>
                    </c:if>
                </ul>
            </div>

        </div>
    </nav>
</header>