<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%@ include file="../header.jsp" %>
<main class="container">
    <h2 class="text-center">Personal cabinet</h2>
    <p>First name: ${user.firstName}</p>
    <p>Last name: ${user.lastName}</p>
    <p>Login: ${user.login}</p>
    <form method="GET">
        <input type="hidden" name="command" value="displayStudentsCourses">
        <label for="courses-select">Display courses: </label>
        <select name="displayUserCourses" id="courses-select" class="form-select mb-3">
            <option value="1">Not started</option>
            <option selected value="2">Ongoing</option>
            <option value="completed">Completed</option>
        </select>
        <button type="submit" class="btn btn-primary">Display</button>
    </form>
    <c:set var="studentsCoursesListSize">${fn:length(studentsCourses)}</c:set>
    <c:choose>
        <c:when test="${studentsCoursesListSize > 0}">
        <table class="table table-bordered border-primary ">
            <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>Theme</th>
                <th>Start date</th>
                <th>End date</th>
                <th>Description</th>
                <th>Tutor first name</th>
                <th>Tutor last name</th>
                <th>Students enrolled</th>
                <th>Mark</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="count" value="0"></c:set>
            <c:forEach items="${studentsCourses}" var="studentsCourse">
                <tr>
                    <c:set var="count" value="${count + 1}"></c:set>
                    <td>${count}</td>
                    <td>${studentsCourse.course.name}</td>
                    <td>${studentsCourse.course.theme.name}</td>
                    <td>${studentsCourse.course.startDate}</td>
                    <td>${studentsCourse.course.endDate}</td>
                    <td>${studentsCourse.course.description}</td>
                    <td>${studentsCourse.course.tutor.firstName}</td>
                    <td>${studentsCourse.course.tutor.lastName}</td>
                    <td>${studentsCourse.course.studentCount}</td>
                    <td>${studentsCourse.mark}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:when>
        <c:otherwise>
            <div class="alert alert-light" role="alert">
                No courses to display.
            </div>
        </c:otherwise>
    </c:choose>
</main>
</html>