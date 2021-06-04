<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%@ include file="../header.jsp" %>
<body>
<main class="container">
    <h2 class="text-center">Journal for ${pageContext.request.getParameter("courseName")} course</h2>
    <c:set var="studentsByCourseSize">${fn:length(studentsByCourse)}</c:set>
    <c:choose>
        <c:when test="${studentsByCourseSize > 0}">
            <table class="table table-bordered border-secondary">
                <thead>
                <tr>
                    <th>#</th>
                    <th>First name</th>
                    <th>Last name</th>
                    <th>Mark</th>
                    <th class="text-center">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${studentsByCourse}" var="studentByCourse">
                    <tr>
                        <c:set var="count" value="${count + 1}"></c:set>
                        <td>${count}</td>
                        <td>${studentByCourse.student.firstName}</td>
                        <td>${studentByCourse.student.lastName}</td>
                        <form action="?firstName=${studentByCourse.student.firstName}&lastName=${studentByCourse.student.lastName}">
                            <input name="command" type="hidden" value="updateMark"/>
                            <input name="studId" type="hidden" value="${studentByCourse.student.id}">
                            <input name="courseId" type="hidden" value="${studentByCourse.course.id}">
                            <td><c:if test="${studentByCourse.mark != 0}">
                                <c:set var="mark" value="${studentByCourse.mark}"/>
                            </c:if>
                                <input name="mark" size="1" type="number" min="1" max="100" value="${mark}">
                            </td>
                            <td class="text-center">
                                <button type="submit" class="btn btn-primary">Update mark</button>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="alert alert-light" role="alert">
                No students to display.
            </div>
        </c:otherwise>
    </c:choose>
</main>
</body>