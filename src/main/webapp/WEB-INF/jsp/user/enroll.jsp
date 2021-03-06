<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <c:set var="pageTitle" scope="page" value="enroll_page"/>
    <%@ include file="../header.jsp"%>
    <fmt:message key="go_back_to_available_courses" var="go_back_to_available_courses"/>
    <fmt:message key="enroll_success" var="enroll_success"/>
    <main class="container">
        <div class="alert alert-success" role="alert">
            <p>${enroll_success} ${pageContext.request.getParameter("courseName")}</p>
            <hr>
            <a href="../main_page">${go_back_to_available_courses}</a>
        </div>
        <%@include file="../footer.jsp"%>
    </main>
</html>