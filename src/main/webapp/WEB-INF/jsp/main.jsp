<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<c:set var="pageTitle" value="main_page"/>
<%@ include file="header.jsp" %>
<fmt:message key="available_courses" var="available_courses"/>
<fmt:message key="filter" var="filter"/>
<fmt:message key="apply_filter" var="apply_filter"/>
<fmt:message key="sort_by" var="sort_by"/>
<fmt:message key="by_tutor" var="by_tutor"/>
<fmt:message key="by_theme" var="by_theme"/>
<fmt:message key="course_name_az" var="course_name_az"/>
<fmt:message key="course_name_za" var="course_name_za"/>
<fmt:message key="course_duration_long_first" var="course_duration_long_first"/>
<fmt:message key="course_duration_short_first" var="course_duration_short_first"/>
<fmt:message key="enrolled_students_few_first" var="enrolled_students_few_first"/>
<fmt:message key="enrolled_students_more_first" var="enrolled_students_more_first"/>
<fmt:message key="course_name" var="course_name"/>
<fmt:message key="start_date" var="start_date"/>
<fmt:message key="end_date" var="end_date"/>
<fmt:message key="description" var="description"/>
<fmt:message key="tutor_first_name" var="tutor_first_name"/>
<fmt:message key="tutor_last_name" var="tutor_last_name"/>
<fmt:message key="students_enrolled" var="students_enrolled"/>
<fmt:message key="enroll" var="enroll"/>
<fmt:message key="all" var="all"/>
<fmt:message key="create_stud_account" var="create_stud_account"/>
<fmt:message key="no_courses_to_display" var="no_courses_to_display"/>

<main class="container mx-auto">
    <h2 class="text-center">${available_courses}</h2>
    <div class="row">
        <div class="p-1 ">
            <div>
                <form action="main_page" method="GET">
                    <input type="hidden" name="page" value="1">
                    <c:set var="selectedThemeId" value="${pageContext.request.getParameter(\"themeId\")}"></c:set>
                    <c:set var="selectedTutorId" value="${pageContext.request.getParameter(\"tutorId\")}"></c:set>
                    <label>${filter}</label>
                    <select id="tutor-filter" name="tutorId" class="form-select mb-1" id="tutor-filter">
                        <option value="" disabled selected>${by_tutor}</option>
                        <option value="">${all}</option>
                        <c:forEach items="${tutors}" var="tutor">
                            <option
                                    <c:if test="${selectedTutorId eq tutor.id}">selected</c:if>
                                    value="${tutor.id}">${tutor.firstName} ${tutor.lastName}
                            </option>
                        </c:forEach>
                    </select>
                    <select id="theme-filter" name="themeId" class="form-select mb-1" id="theme-filter" class="mb-1">
                        <option value="" disabled selected>${by_theme}</option>
                        <option value="">${all}</option>
                        <c:forEach items="${themes}" var="theme">
                            <option
                                    <c:if test="${selectedThemeId eq theme.id}">selected</c:if>
                                    value="${theme.id}">${theme.name}</option>
                        </c:forEach>
                    </select>
                    <label for="sort-option">${sort_by}</label>
                    <select id="sort-option" name="sortOption" class="form-select col-lg-3 mb-2">
                        <option
                                <c:if test="${pageContext.request.getParameter(\"sortOption\") eq \"courseNameAsc\"}">selected</c:if>
                                value="courseNameAsc">${course_name_az}
                        </option>
                        <option
                                <c:if test="${pageContext.request.getParameter(\"sortOption\") eq \"courseNameDesc\"}">selected</c:if>
                                value="courseNameDesc">${course_name_za}
                        </option>
                        <option
                                <c:if test="${pageContext.request.getParameter(\"sortOption\") eq \"courseDurationAsc\"}">selected</c:if>
                                value="courseDurationAsc">${course_duration_short_first}
                        </option>
                        <option
                                <c:if test="${pageContext.request.getParameter(\"sortOption\") eq \"courseDurationDesc\"}">selected</c:if>
                                value="courseDurationAsc">${course_duration_long_first}
                        </option>
                        <option
                                <c:if test="${pageContext.request.getParameter(\"sortOption\") eq \"courseStudentsAsc\"}">selected</c:if>
                                value="courseStudentsAsc">${enrolled_students_few_first}
                        </option>
                        <option
                                <c:if test="${pageContext.request.getParameter(\"sortOption\") eq \"courseStudentsDesc\"}">selected</c:if>
                                value="courseStudentsDesc">${enrolled_students_more_first}
                        </option>
                    </select>
                    <button type="submit" class="btn btn-primary mb-2">${apply_filter}</button>
                </form>
            </div>
            <c:set var="coursesPageSize">${fn:length(coursesPage)}</c:set>
            <c:choose>
                <c:when test="${coursesPageSize > 0}">
            <table class="table table-bordered border-primary ">
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
                    <th>${action}</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="currentPage" value="${pageContext.request.getParameter(\"page\")}"/>
                <c:set var="count" value="${3 * (currentPage - 1)}"/>
                <c:set var="userRole" value="${user.role}"/>
                <c:forEach items="${coursesPage}" var="course">
                    <tr>
                        <c:set var="count" value="${count + 1}"/>
                        <td>${count}</td>
                        <td>${course.name}</td>
                        <td>${course.theme.name}</td>
                        <td><tags:formatLocalDate date="${course.startDate}"/></td>
                        <td><tags:formatLocalDate date="${course.endDate}"/></td>
                        <td>${course.description}</td>
                        <c:choose>
                            <c:when test="${course.tutor ne null}">
                                <td>${course.tutor.firstName}</td>
                                <td>${course.tutor.lastName}</td>
                            </c:when>
                            <c:otherwise>
                                <td colspan="2" class="text-center">${no_tutor_assigned}</td>
                            </c:otherwise>
                        </c:choose>

                        <td>${course.studentCount}</td>
                        <td>
                            <c:choose >
                                <c:when test="${userRole eq \'STUDENT\'}">
                                    <form class="text-center" method="POST">
                                        <input type="hidden" name="command" value="enroll">
                                        <input type="hidden" name="courseId" value="${course.id}">
                                        <input type="hidden" name="courseName" value="${course.name}">

                                        <button type="submit" class="btn btn-outline-info">
                                            ${enroll}
                                        </button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <p>${create_stud_account}</p>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <nav>
                <c:set var="currentPage" value="${pageContext.request.getParameter(\"page\")}"/>
                <c:set var="sortOption" value="${pageContext.request.getParameter(\"sortOption\")}"/>
                <c:set var="tutorId" value="${pageContext.request.getParameter(\"tutorId\")}"/>
                <c:set var="themeId" value="${pageContext.request.getParameter(\"themeId\")}"/>
                <ul class="pagination">
                    <c:if test="${currentPage == 1}">
                        <c:set var="prevDisabled" value="disabled"></c:set>
                    </c:if>
                    <li class="page-item ${prevDisabled}"><a class="page-link"
                                                             href="main_page?page=${currentPage - 1}&sortOption=${sortOption}&themeId=${themeId}&tutorId=${tutorId}">${previous}</a>
                    </li>

                    <c:forEach begin="1" end="${pageCount}" var="num" varStatus="loop">
                        <c:if test="${num == currentPage}">
                            <c:set var="active" value="active"></c:set>
                        </c:if>
                        <li class="page-item ${active}"><a class="page-link"
                                                           href="main_page?page=${loop.index}&sortOption=${sortOption}&themeId=${themeId}&tutorId=${tutorId}">${loop.index}</a>
                        </li>
                        <c:set var="active" value="null"></c:set>
                    </c:forEach>

                    <c:if test="${currentPage == pageCount}">
                        <c:set var="nextDisabled" value="disabled"></c:set>
                    </c:if>
                    <li class="page-item ${nextDisabled}"><a class="page-link"
                                                             href="main_page?page=${currentPage + 1}&sortOption=${sortOption}&themeId=${themeId}&tutorId=${tutorId}">${next}</a>
                    </li>
                </ul>
            </nav>
                </c:when>
                <c:otherwise>
                    ${no_courses_to_display}
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <%@include file="footer.jsp"%>
</main>
</body>
</html>