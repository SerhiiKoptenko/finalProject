<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="page" value="delete_course"/>
<html lang="en">
<%@ include file="../header.jsp"%>
<fmt:message key="delete_course_confirmation" var="delete_course_confirmation"/>
<body>
<main class="container mx-auto">
    <div class="alert alert-primary mt-3" role="alert">
        ${delete_course_confirmation} ${deletedCourse.name}?
    </div>
    <form action="manage_courses" method="POST">
        <input type="hidden" name="command" value="deleteCourse">
        <input type="hidden" value="${deletedCourse.id}" name="courseId">
        <button type="submit" class="btn btn-danger">${yes}</button>
        <a href="manage_courses" class="btn btn-primary">${no}</a>
    </form>
    <%@include file="../footer.jsp"%>
</main>
</body>

</html>