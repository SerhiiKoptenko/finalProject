<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<c:set var="pageTitle" scope="page" value="leave_course_page"/>
<%@ include file="../header.jsp" %>
<fmt:message key="leave_course_confirmation" var="leave_course_confirmation"/>
<body>
<main class="container">
    <div class="alert alert-danger" role="alert">
        ${leave_course_confirmation} ${pageContext.request.getParameter("courseName")}?
        <form method="POST">
            <input type="hidden" name="courseId" value="${pageContext.request.getParameter('courseId')}">
            <input type="hidden" name="studId" value="${pageContext.request.getParameter('studId')}">
            <input type="hidden" name="courseName" value="${pageContext.request.getParameter("courseName")}">
            <input type="hidden" name="command" value="leaveCourse">
            <div>
                <button type="submit" class="btn btn-danger">${yes}</button>
                <a class="btn btn-light" href="${pageContext.request.contextPath}/user/personal_cabinet">${no}</a>
            </div>
        </form>
    </div>
    <%@include file="../footer.jsp"%>
</main>
</body>