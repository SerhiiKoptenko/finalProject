<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%@ include file="../header.jsp" %>
<body>
<main class="container">
    <div class="alert alert-danger" role="alert">
        Are you sure you want to leave course ${pageContext.request.getParameter("courseName")}?
        <form method="POST">
            <input type="hidden" name="courseId" value="${pageContext.request.getParameter('courseId')}">
            <input type="hidden" name="studId" value="${pageContext.request.getParameter('studId')}">
            <input type="hidden" name="courseName" value="${pageContext.request.getParameter("courseName")}">
            <input type="hidden" name="command" value="leaveCourse">
            <div>
                <button type="submit" class="btn btn-danger">Yes</button>
                <a class="btn btn-light" href="${pageContext.request.contextPath}/user/personal_cabinet">No</a>
            </div>
        </form>
    </div>
</main>
</body>