<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" scope="page" value="Admin basis"/>
<%@ include file="../header.jsp"%>
<body>
<main class="container mx-auto">
    <div class="alert alert-primary mt-3" role="alert">
        Are you sure you want to delete ${deletedCourse.name} course?
    </div>
    <form action="manage_courses" method="POST">
        <input type="hidden" name="command" value="deleteCourse">
        <input type="hidden" value="${deletedCourse.id}" name="courseId">
        <button type="submit" class="btn btn-danger">Yes</button>
        <a href="manage_courses" class="btn btn-primary">No</a>
    </form>
    <%@include file="../footer.jsp"%>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>

</html>