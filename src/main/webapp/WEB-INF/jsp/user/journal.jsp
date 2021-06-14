<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cust" uri="/WEB-INF/tags.tld" %>
<html lang="en">
<c:set var="pageTitle" scope="page" value="journal"/>
<%@ include file="../header.jsp" %>
<fmt:message key="journal_for" var="journal_for"/>
<fmt:message key="no_students_to_display" var="no_students_to_display"/>
<fmt:message key="update_mark" var="update_mark"/>
<main class="container">
    <h2 class="text-center">${journal_for} ${course.name}</h2>
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


                            <c:set var="endDateAfterToday" scope="page">
                                <cust:isDateAfterToday date="${course.endDate}"/>
                            </c:set>
                            <c:choose>
                                <c:when test="${endDateAfterToday}">
                                    <td class="text-center">-</td>
                                    <td class="text-center">-</td>
                                </c:when>
                                <c:otherwise>
                                    <form id="update-mark" method="post">
                                    <td class="text-center"><c:if test="${studentByCourse.mark != 0}">
                                        <c:set var="mark" value="${studentByCourse.mark}"/>
                                    </c:if>
                                        <input class="text-center" name="mark" size="1" type="number" min="1" max="100" value="${mark}">
                                    </td>
                                    <td class="text-center">

                                            <input name="command" type="hidden" value="updateMark"/>
                                            <input name="studId" type="hidden" value="${studentByCourse.student.id}">
                                            <input name="courseId" type="hidden" value="${studentByCourse.course.id}">
                                        <button type="submit" class="btn btn-primary">${update_mark}</button>
                                    </td>
                                    </form>
                                </c:otherwise>
                            </c:choose>

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
</html>