<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%@ include file="../header.jsp" %>
<fmt:message key="journal_for" var="journal_for"/>
<fmt:message key="no_students_to_display" var="no_students_to_display"/>
<body>
<main class="container">
    <h2 class="text-center">${journal_for} ${pageContext.request.getParameter("courseName")}</h2>
    <c:set var="studentsByCourseSize">${fn:length(studentsByCourse)}</c:set>
    <c:choose>
        <c:when test="${studentsByCourseSize > 0}">
            <table class="table table-bordered border-secondary">
                <thead>
                <tr>
                    <th>#</th>
                    <th>${first_name}</th>
                    <th>${last_name}</th>
                    <th>${mark}</th>
                    <th class="text-center">${action}</th>
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
               ${no_students_to_display}.
            </div>
        </c:otherwise>
    </c:choose>
    <%@include file="../footer.jsp"%>
</main>
</body>