<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cust" uri="/WEB-INF/tags.tld" %>
<!DOCTYPE html>
<html lang="en">
<%@ include file="header.jsp" %>
<c:set var="pageTitle" scope="page" value="Main page"/>

<body>
<main class="container mx-auto">
    <h2 class="text-center">Available courses</h2>
    <div class="row">
        <div class="p-1 ">

            <div>
                <form action="main_page" method="GET">
                    <input type="hidden" name="page" value="1">
                    <c:set var="selectedThemeId" value="${pageContext.request.getParameter(\"themeId\")}"></c:set>
                    <c:set var="selectedTutorId" value="${pageContext.request.getParameter(\"tutorId\")}"></c:set>
                    <label>Filter:</label>
                    <select id="tutor-filter" name="tutorId" class="form-select mb-1" id="tutor-filter">
                        <option value="" disabled selected>By tutor:</option>
                        <option value="">All</option>
                        <c:forEach items="${tutors}" var="tutor">
                            <option
                                    <c:if test="${selectedTutorId eq tutor.id}">selected</c:if>
                                    value="${tutor.id}">${tutor.firstName} ${tutor.lastName} (${tutor.login})
                            </option>
                        </c:forEach>
                    </select>
                    <select id="theme-filter" name="themeId" class="form-select mb-1" id="theme-filter" class="mb-1">
                        <option value="" disabled selected>By theme:</option>
                        <option value="">All</option>
                        <c:forEach items="${themes}" var="theme">
                            <option
                                    <c:if test="${selectedThemeId eq theme.id}">selected</c:if>
                                    value="${theme.id}">${theme.name}</option>
                        </c:forEach>
                    </select>
                    <label for="sort-option">Sort by:</label>
                    <select id="sort-option" name="sortOption" class="form-select col-lg-3 mb-2">
                        <option
                                <c:if test="${pageContext.request.getParameter(\"sortOption\") eq \"courseNameAsc\"}">selected</c:if>
                                value="courseNameAsc">Course name A-Z
                        </option>
                        <option
                                <c:if test="${pageContext.request.getParameter(\"sortOption\") eq \"courseNameDesc\"}">selected</c:if>
                                value="courseNameDesc">Course name Z-A
                        </option>
                        <option
                                <c:if test="${pageContext.request.getParameter(\"sortOption\") eq \"courseDurationAsc\"}">selected</c:if>
                                value="courseDurationAsc">Course duration (shortest first)
                        </option>
                        <option
                                <c:if test="${pageContext.request.getParameter(\"sortOption\") eq \"courseDurationDesc\"}">selected</c:if>
                                value="courseDurationAsc">Course duration (longest first)
                        </option>
                        <option
                                <c:if test="${pageContext.request.getParameter(\"sortOption\") eq \"courseStudentsAsc\"}">selected</c:if>
                                value="courseStudentsAsc">Enrolled students (fewer first)
                        </option>
                        <option
                                <c:if test="${pageContext.request.getParameter(\"sortOption\") eq \"courseStudentsDesc\"}">selected</c:if>
                                value="courseStudentsDesc">Enrolled students (more first)
                        </option>
                    </select>
                    <button type="submit" class="btn btn-primary mb-2">Apply filter</button>
                </form>
            </div>
            <table class="table table-bordered border-primary ">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Theme</th>
                    <th>Start date</th>
                    <th>End date</th>
                    <th>Description</th>
                    <th>Tutor login</th>
                    <th>Tutor first name</th>
                    <th>Tutor last name</th>
                    <th>Students enrolled</th>
                    <td>Status</td>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="currentPage" value="${pageContext.request.getParameter(\"page\")}"/>
                <c:set var="count" value="${5 * (currentPage - 1)}"></c:set>
                <c:forEach items="${coursesPage}" var="course">
                    <tr>
                        <c:set var="count" value="${count + 1}"></c:set>
                        <c:set var="isStartDateAfterToday"><cust:isDateAfterToday date="${course.startDate}"/></c:set>
                        <c:set var="isEndDateAfterToday"><cust:isDateAfterToday date="${course.endDate}"/></c:set>
                        <td>${count}</td>
                        <td>${course.name}</td>
                        <td>${course.theme.name}</td>
                        <td>${course.startDate}</td>
                        <td>${course.endDate}</td>
                        <td>${course.description}</td>
                        <td>${course.tutor.login}</td>
                        <td>${course.tutor.firstName}</td>
                        <td>${course.tutor.lastName}</td>
                        <td>${fn:length(course.students)}</td>
                        <td>
                            <c:if test="${!isStartDateAfterToday}">
                                <c:if test="${isEndDateAfterToday}">
                                    Ongoing
                                </c:if>
                                <c:if test="${!isEndDateAfterToday}">
                                    <p class="text-secondary">Completed</p>
                                </c:if>
                            </c:if>
                            <c:if test="${isStartDateAfterToday}">
                                Not started
                            </c:if>
                        </td>
                        <td>
                            <form action="main_page" class="text-center" method="POST">
                                <input type="hidden" name="command" value="enroll">
                                <input type="hidden" name="page" value="${currentPage}">
                                <input type="hidden" name="courseId" value="${course.id}">

                                <button type="submit" class="btn btn-outline-info"
                                        <c:if test="${!isStartDateAfterToday}">disabled</c:if>>
                                    Enroll
                                </button>
                            </form>
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
                                                             href="main_page?page=${currentPage - 1}&sortOption=${sortOption}&themeId=${themeId}&tutorId=${tutorId}">Previous</a>
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
                                                             href="main_page?page=${currentPage + 1}&sortOption=${sortOption}&themeId=${themeId}&tutorId=${tutorId}">Next</a>
                    </li>
                </ul>
            </nav>
        </div>
</main>
<script src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/static/js/app.js"></script>
</body>
</html>