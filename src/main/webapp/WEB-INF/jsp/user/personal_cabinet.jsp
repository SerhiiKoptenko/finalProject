<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%@ include file="../header.jsp" %>
<body>
<main class="container">
    <h2 class="text-center">Personal cabinet</h2>
    <p>First name: ${user.firstName}</p>
    <p>Last name: ${user.lastName}</p>
    <p>Login: ${user.login}</p>
    <c:if test="${user.role eq \"STUDENT\"}">
        <form method="GET">
            <input type="hidden" name="command" value="displayStudentsCourses">
            <label for="courses-select">Display courses: </label>
            <select name="displayedCourses" id="courses-select" class="form-select mb-3">
                <option value="not_started">Not started</option>
                <option value="ongoing">Ongoing</option>
                <option value="completed">Completed</option>
            </select>
            <button type="submit" class="btn btn-primary">Display</button>
        </form>

        <c:set var="studentsCoursesListSize">${fn:length(studentsCourses)}</c:set>
        <c:set var="displayedCourses" value="${pageContext.request.getParameter(\"displayedCourses\")}"/>
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
                        <c:if test="${displayedCourses eq \"ongoing\" || displayedCourses eq \"not_started\"}">
                            <th>Action</th>
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
                            <td>${studentsCourse.course.startDate}</td>
                            <td>${studentsCourse.course.endDate}</td>
                            <td>${studentsCourse.course.description}</td>
                            <td>${studentsCourse.course.tutor.firstName}</td>
                            <td>${studentsCourse.course.tutor.lastName}</td>
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
                                <td><button class="btn btn-outline-danger"
                                       type="submit"
                                       role="button">Leave course</button></td>
                                </form>
                            </c:if>
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
    </c:if>

    <c:if test="${pageContext.request.getParameter('leftCourseName') ne null}}">
        <div class="alert alert-warning" role="alert">
            You left ${pageContext.request.getParameter('leftCourseName')} course.
        </div>
    </c:if>

    <c:if test="${user.role eq \"TUTOR\"}">
        <form method="GET">
            <input type="hidden" name="command" value="displayTutorsCourses">
            <label for="tutors-courses-select">Display courses: </label>
            <select name="displayTutorsCourses" id="tutors-courses-select" class="form-select mb-3">
                <option value="not_started">Not started</option>
                <option value="ongoing">Ongoing</option>
                <option value="completed">Completed</option>
            </select>
            <button type="submit" class="btn btn-primary">Display</button>
        </form>
        <c:set var="displayedCourses" value="${pageContext.request.getParameter(\"displayTutorsCourses\")}"/>
        <c:set var="tutorsCoursesListSize">${fn:length(tutorsCourses)}</c:set>
        <c:choose>
            <c:when test="${tutorsCoursesListSize > 0}">
                <table class="table table-bordered border-primary ">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Theme</th>
                        <th>Start date</th>
                        <th>End date</th>
                        <th>Description</th>
                        <th>Students enrolled</th>
                        <c:if test="${displayedCourses eq \"completed\"}">
                            <th class="text-center">Action</th>
                        </c:if>
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
                            <td>${tutorsCourse.startDate}</td>
                            <td>${tutorsCourse.endDate}</td>
                            <td>${tutorsCourse.description}</td>
                            <td>${tutorsCourse.studentCount}</td>
                            <td>
                                <c:if test="${displayedCourses eq \"completed\"}">
                                    <form method="GET" class="text-center" action="personal_cabinet/journal">
                                        <button class="btn btn-outline-success" type="submit">Display journal</button>
                                        <input type="hidden" name="courseId" value="${tutorsCourse.id}">
                                        <input type="hidden" name="courseName" value="${tutorsCourse.name}">
                                    </form>
                                </c:if>
                            </td>
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
    </c:if>
</main>
</body>
</html>