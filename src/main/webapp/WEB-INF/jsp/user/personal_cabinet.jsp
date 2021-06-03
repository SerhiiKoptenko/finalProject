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

    <form method="GET">
        <input type="hidden" name="command" value="displayTutorsCourses">
        <label for="tutors-courses-select">Display courses: </label>
        <select name="displayTutorsCourses" id="tutors-courses-select" class="form-select mb-3">
            <option value="1">Not started</option>
            <option selected value="2">Ongoing</option>
            <option value="completed">Completed</option>
        </select>
        <button type="submit" class="btn btn-primary">Display</button>
    </form>
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
                    <th>Action</th>
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
                            <form method="GET" class="text-center">
                                <input type="hidden" name="command" value="displayTutorsJournal">
                                <input type="hidden" name="courseId" value="${tutorsCourse.id}">
                                <button class="btn btn-outline-success" type="submit">Display journal</button>
                            </form>
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
                            <td><input name="mark" size="1" type="number" min="1" max="100" value="${studentByCourse.mark}"></td>
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
                No courses to display.
            </div>
        </c:otherwise>
    </c:choose>
</main>
</html>