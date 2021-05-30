<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" scope="page" value="Manage courses"/>
<%@ include file="../header.jsp" %>
<body>
<main class="container mx-auto">
    <form id="edit-course-form" action="edit_course" method="POST">
        <input type="hidden" name="page" value="${pageContext.request.getParameter("page")}">
        <input type="hidden" name="command" value="updateCourse">
        <input type="hidden" name="courseId" value="${editedCourse.id}">
        <div class="mb-2">
            <h1 class="text-center mt-3">Edit course</h1>

            <label for="course-name" class="mt-5 mx-2 py-1">Course name:</label>
            <div class="input-group mb-2">
                <input id="course-name" name="courseName" type="text" class="form-control" value="${editedCourse.name}">
            </div>

            <label for="course-theme" class="mx-2 py-1">Course theme: </label>
            <div class="input-group mb-2 ">
                <select name="themeId" class="form-select" id="course-theme">
                    <option selected value="${editedCourse.theme.id}">${editedCourse.theme.name}</option>
                    <c:forEach items="${themes}" var="theme">
                        <option value="${theme.id}">${theme.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        </div>

        <label for="start-date" class="mx-2 py-1 ">Start date:</label>
        <div class="input-group mb-2">
            <input id="start-date" name="startDate" type="date" class="form-control" value="${editedCourse.startDate}">
        </div>

        <label for="end-date" class="mx-2 py-1 ">End date:</label>
        <div class="input-group mb-2">
            <input id="end-date" name="endDate" type="date" class="form-control" value="${editedCourse.endDate}">
        </div>

        <label for="tutor" class="mx-2 py-1">Tutor: </label>
        <div class="input-group mb-2 ">
            <select name="tutorId" class="form-select" id="tutor">
                <option value="${editedCourse.tutor.id}"
                        selected>
                    <c:if test="${editedCourse.tutor ne null}">${editedCourse.tutor.firstName} ${editedCourse.tutor.lastName}
                        (${editedCourse.tutor.login})
                    </c:if>
                </option>
                <c:forEach items="${tutors}" var="tutor">
                    <option value="${tutor.id}">${tutor.firstName} ${tutor.lastName} (${tutor.login})</option>
                </c:forEach>
            </select>
        </div>
        <label for="description" class="mx-2 py-1">Course description: </label>
        <div class="input-group ">
            <textarea id="description" name="description" class="form-control">${editedCourse.description}</textarea>
        </div>
        <button type="submit" class="btn btn-primary col-lg-2 offset-lg-5 mt-2 mb-2">Update</button>
    </form>
    <c:if test="${pageContext.request.getParameter(\"result\") eq \"errorInvalidData\"}">
        <div class="alert alert-danger mt-2 mb-2" role="alert">
            <c:if test="${pageContext.request.getParameter(\"invalid_courseName\") ne null}">
                Invalid course name.<br>
            </c:if>
            <c:if test="${pageContext.request.getParameter(\"invalid_startDateOrEndDate\") ne null}">
                Invalid start date or end date.<br>
            </c:if>
        </div>
    </c:if>
    <c:if test="${pageContext.request.getParameter(\"result\") eq \"success\"}">
        <div class="alert alert-success mt-2 mb-2" role="alert">
            Course updated successfully.
        </div>
    </c:if>
</main>
</body>