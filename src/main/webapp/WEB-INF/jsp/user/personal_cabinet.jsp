<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="page" value="personal_cabinet"/>
<html lang="en">
<%@ include file="../header.jsp" %>
<fmt:message key="display_courses" var="display_courses"/>
<fmt:message key="not_started" var="not_started"/>
<fmt:message key="ongoing" var="ongoing"/>
<fmt:message key="completed" var="completed"/>
<fmt:message key="display" var="display"/>
<fmt:message key="leave_course" var="leave_course"/>
<fmt:message key="no_courses_to_display" var="no_courses_to_display"/>
<fmt:message key="you_left" var="you_left"/>
<fmt:message key="display_journal" var="display_journal"/>
<body>
<main class="container">
    <h2 class="text-center">${personal_cabinet}</h2>
    <p>${first_name}: ${user.firstName}</p>
    <p>${last_name}: ${user.lastName}</p>
    <p>${login}: ${user.login}</p>
    <c:set var="displayedCourses" value="${pageContext.request.getParameter(\"displayedCourses\")}"/>
    <c:if test="${user.role eq \"STUDENT\"}">
        <form method="GET">
            <input type="hidden" name="command" value="displayStudentsCourses">
            <label for="courses-select">${display_courses}: </label>
            <select name="displayedCourses" id="courses-select" class="form-select mb-3">
                <option value="not_started" <c:if test="${displayedCourses eq 'not_started'}">selected</c:if> >${not_started}</option>
                <option value="ongoing" <c:if test="${displayedCourses eq 'ongoing'}">selected</c:if> >${ongoing}</option>
                <option value="completed" <c:if test="${displayedCourses eq 'completed'}">selected</c:if> >${completed}</option>
            </select>
            <button type="submit" class="btn btn-primary">${display}</button>
        </form>

        <c:set var="studentsCoursesListSize">${fn:length(studentsCourses)}</c:set>
        <c:choose>
            <c:when test="${studentsCoursesListSize > 0}">
                <table class="table table-bordered border-primary mt-3">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>${course_name}</th>
                        <th>${theme_name}</th>
                        <th>${start_date}</th>
                        <th>${end_date}</th>
                        <th>${description}</th>
                        <th>${tutor_first_name}</th>
                        <th>${tutor_last_name}</th>
                        <th>${students_enrolled}</th>
                        <th>${mark}</th>
                        <c:if test="${displayedCourses eq \"ongoing\" || displayedCourses eq \"not_started\"}">
                            <th>${action}</th>
                        </c:if>
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
                            <td><tags:formatLocalDate date="${studentsCourse.course.startDate}"/></td>
                            <td><tags:formatLocalDate date="${studentsCourse.course.endDate}"/></td>
                            <td>${studentsCourse.course.description}</td>
                            <c:choose>
                            <c:when test="${studentsCourse.course.tutor ne null}">
                                <td>${studentsCourse.course.tutor.firstName}</td>
                                <td>${studentsCourse.course.tutor.lastName}</td>
                            </c:when>
                            <c:otherwise>
                                <td colspan="2" class="text-center">${no_tutor_assigned}</td>
                            </c:otherwise>
                        </c:choose>
                            <td>${studentsCourse.course.studentCount}</td>
                            <td class="text-center"><c:choose>
                                <c:when test="${studentsCourse.mark != 0}">${studentsCourse.mark}</c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                            </td>
                            <c:if test="${displayedCourses eq \"ongoing\" || displayedCourses eq \"not_started\"}">
                                <form action="personal_cabinet/leave_course" METHOD="POST">
                                    <input type="hidden" name="studId" value="${studentsCourse.student.id}">
                                    <input type="hidden" name="courseName" value="${studentsCourse.course.name}">
                                    <input type="hidden" name="courseId" value="${studentsCourse.course.id}">
                                    <td>
                                        <button class="btn btn-outline-danger"
                                                type="submit"
                                                role="button">${leave_course}</button>
                                    </td>
                                </form>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="alert alert-light" role="alert">
                        <c:if test="${displayedCourses ne null}">
                            ${no_courses_to_display}
                        </c:if>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${pageContext.request.getParameter('leftCourseName') ne null}}">
        <div class="alert alert-warning" role="alert">
                ${you_left} ${pageContext.request.getParameter('leftCourseName')} ${course_word}
        </div>
    </c:if>

    <c:if test="${user.role eq \"TUTOR\"}">
        <form method="GET">
            <input type="hidden" name="command" value="displayTutorsCourses">
            <label for="tutors-courses-select">${display_courses}: </label>
            <c:set var="displayedCourses" value="${pageContext.request.getParameter(\"displayCourses\")}"/>
            <select name="displayCourses" id="tutors-courses-select" class="form-select mb-3">
                <option value="not_started" <c:if test="${displayedCourses eq 'not_started'}">selected</c:if> >${not_started}</option>
                <option value="ongoing" <c:if test="${displayedCourses eq 'ongoing'}">selected</c:if> >${ongoing}</option>
                <option value="completed" <c:if test="${displayedCourses eq 'completed'}">selected</c:if> >${completed}</option>
            </select>
            <button type="submit" class="btn btn-primary mb-3">${display}</button>
        </form>
        <c:set var="tutorsCoursesListSize">${fn:length(tutorsCourses)}</c:set>
        <c:choose>
            <c:when test="${tutorsCoursesListSize > 0}">
                <table class="table table-bordered border-primary ">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>${course_name}</th>
                        <th>${theme_name}</th>
                        <th>${start_date}</th>
                        <th>${end_date}</th>
                        <th>${description}</th>
                        <th>${students_enrolled}</th>
                            <th class="text-center">${action}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="count" value="0"></c:set>
                    <c:forEach items="${tutorsCourses}" var="tutorsCourse">
                        <tr>
                            <c:set var="count" value="${count + 1}"></c:set>
                            <td>${count}</td>
                            <td>${tutorsCourse.name}</td>
                            <td>${tutorsCourse.theme.name}</td>
                            <td><tags:formatLocalDate date="${tutorsCourse.startDate}"/></td>
                            <td><tags:formatLocalDate date="${tutorsCourse.endDate}"/></td>
                            <td>${tutorsCourse.description}</td>
                            <td>${tutorsCourse.studentCount}</td>
                                <td>
                                    <form method="GET" class="text-center" action="personal_cabinet/journal">
                                        <button class="btn btn-outline-success" type="submit">${display_journal}</button>
                                        <input type="hidden" name="courseId" value="${tutorsCourse.id}">
                                        <input type="hidden" name="courseName" value="${tutorsCourse.name}">
                                    </form>
                                </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <c:if test="${displayedCourses ne null}">
                    ${no_courses_to_display}
                </c:if>
            </c:otherwise>
        </c:choose>
    </c:if>
    <%@include file="../footer.jsp" %>
</main>
</body>
</html>