<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="page" value="Manage courses"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../header.jsp" %>
<fmt:message key="add_new_theme" var="add_new_theme"/>
<fmt:message key="add_course_name" var="add_course_name"/>
<fmt:message key="add_tutor" var="add_tutor"/>
<fmt:message key="course_description" var="course_description"/>
<fmt:message key="add_new_course" var="add_new_course"/>
<fmt:message key="remove_theme" var="remove_theme"/>
<fmt:message key="invalid_course_name" var="invalid_course_name"/>
<fmt:message key="invalid_course_date" var="invalid_course_date"/>
<fmt:message key="course_added_success" var="course_added_success"/>
<fmt:message key="course_update_success" var="course_update_success"/>
<fmt:message key="course_delete_success" var="course_delete_success"/>
<fmt:message key="tutor_login" var="tutor_login"/>
<fmt:message key="update" var="update"/>
<fmt:message key="delete" var="delete"/>
<fmt:message key="theme_exists" var="theme_exists"/>
<fmt:message key="close" var="close"/>
<fmt:message key="add" var="add"/>
<fmt:message key="enter_theme_name" var="enter_theme_name"/>
<fmt:message key="enter_theme_name" var="enter_theme_name"/>
<fmt:message key="cant_delete_course" var="cant_delete_course"/>
<fmt:message key="cant_delete_theme" var="cant_delete_theme"/>
<main class="container mx-auto">
    <div class="row">
        <form id="add-course-form" action="/admin/manage_courses" method="POST" class="d-none">
            <input type="hidden" name="command" value="addCourse">
            <input type="hidden" name="page" value="${currentPage}">
            <button id="hide-add-course" type="button" class="btn-close mb-5  mt-1 d-block" style="float: right;"
                    aria-label="Close"></button>
            <div class="mb-2">
                <label for="course-theme" class="mt-5 mx-2 py-1">${theme_name}:</label>
                <div class="input-group mb-2 ">
                    <select name="themeId" class="form-select" id="course-theme">
                        <c:forEach items="${themes}" var="theme">
                            <option value="${theme.id}">${theme.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div>
                <button id="add-theme-button" type="button" class="btn btn-outline-success" data-bs-toggle="modal"
                        data-bs-target="#add-theme-modal">
                    <svg xmlns="http://www.w3.org/2000/svg " width="16 " height="16 " fill="currentColor "
                         class="bi bi-plus-circle " viewBox="0 0 16 16 ">
                        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z "/>
                        <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z "/>
                    </svg>
                    ${add_new_theme}
                </button>
            </div>
            <label for="course-name" class="mx-2 py-1">${add_course_name}:</label>
            <div class="input-group mb-2">
                <input name="courseName" id="course-name" type="text" class="form-control">
            </div>

            <label for="start-date" class="mx-2 py-1 ">${start_date}:</label>
            <div class="input-group mb-2">
                <input name="startDate" id="start-date" type="date" class="form-control">
            </div>

            <label for="end-date" class="mx-2 py-1 ">${end_date}:</label>
            <div class="input-group mb-2">
                <input name="endDate" id="end-date" type="date" class="form-control">
            </div>

            <label for="tutor" class="mx-2 py-1">${add_tutor}: </label>
            <div class="input-group mb-2 ">
                <select name="tutorId" class="form-select" id="tutor">
                    <option value selected>None</option>
                    <c:forEach items="${tutors}" var="tutor">
                        <option value="${tutor.id}">${tutor.firstName} ${tutor.lastName} (${tutor.login})</option>
                    </c:forEach>
                </select>
            </div>
            <label for="description" class="mx-2 py-1">${course_description}:</label>
            <div class="input-group ">
                <textarea name="description" id="description" class="form-control"></textarea>
            </div>
            <button type="submit" class="btn btn-success col-lg-2 offset-lg-5 mt-2 mb-2">${add}</button>

        </form>
        <button id="disp-add-course" type="button " class="btn btn-outline-success mx-2 mt-1 p-1 col-lg-2 col-md-2">
            <svg xmlns="http://www.w3.org/2000/svg" width="16 " height="16 " fill="currentColor "
                 class="bi bi-plus-circle " viewBox="0 0 16 16 ">
                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z "/>
                <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z "/>
            </svg>
            ${add_new_course}
        </button>
        <button id="remove-theme-button" type="button" class="btn btn-outline-danger mx-2 mt-1 p-1 col-lg-2 col-md-2" data-bs-toggle="modal"
                data-bs-target="#remove-theme-modal">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                 class="bi bi-file-minus" viewBox="0 0 16 16">
                <path d="M5.5 8a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1H6a.5.5 0 0 1-.5-.5z"/>
                <path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>
            </svg>
            ${remove_theme}
        </button>
        <c:if test="${pageContext.request.getParameter(\"addResult\") eq \"errorInvalidData\"}">
            <div class="alert alert-danger mt-2 mb-2" role="alert">
                <c:if test="${pageContext.request.getParameter(\"invalid_courseName\") ne null}">
                    ${invalid_course_name}<br>
                </c:if>
                <c:if test="${pageContext.request.getParameter(\"invalid_startDateOrEndDate\") ne null}">
                    ${invalid_course_date}<br>
                </c:if>
            </div>
        </c:if>
        <c:if test="${pageContext.request.getParameter(\"addResult\") eq \"success\"}">
            <div class="alert alert-success mt-2 mb-2" role="alert">
                ${course_added_success}.
            </div>
        </c:if>

        <c:if test="${pageContext.request.getParameter(\"updateResult\") eq \"success\"}">
            <div class="alert alert-success mt-2 mb-2" role="alert">
                ${course_update_success}.
            </div>
        </c:if>

        <c:if test="${pageContext.request.getParameter(\"deleteResult\") eq \"success\"}">
            <div class="alert alert-success mt-2 mb-2" role="alert">
                ${course_delete_success}
            </div>
        </c:if>
        <c:if test="${pageContext.request.getParameter('deleteResult') eq 'cantDelete'}">
            <div class="alert alert-danger mt-2 mb-2" role="alert">
                    ${cant_delete_course}
            </div>
        </c:if>
        <div class="p-1 ">
            <table class="table table-bordered border-primary ">
                <thead>
                <tr>
                    <th>#</th>
                    <th>${course_name}</th>
                    <th>${theme_name}</th>
                    <th>${start_date}</th>
                    <th>${end_date}</th>
                    <th>${description}</th>
                    <th>${tutor_login}</th>
                    <th>${tutor_first_name}</th>
                    <th>${tutor_last_name}</th>
                    <th>${students_enrolled}</th>
                    <th colspan="2" class="text-center">${action}</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="count" value="${fn:length(coursesPage) * (currentPage - 1)}"></c:set>
                <c:forEach items="${coursesPage}" var="course">
                    <tr>
                        <c:set var="count" value="${count + 1}"></c:set>
                        <td>${count}</td>
                        <td>${course.name}</td>
                        <td>${course.theme.name}</td>
                        <td><tags:formatLocalDate date="${course.startDate}"/></td>
                        <td><tags:formatLocalDate date="${course.endDate}"/></td>
                        <td>${course.description}</td>
                        <td>${course.tutor.login}</td>
                        <td>${course.tutor.firstName}</td>
                        <td>${course.tutor.lastName}</td>
                        <td>${course.studentCount}</td>
                        <td>
                            <form action="edit_course" class="text-center" method="POST">
                                <input type="hidden" name="page" value="${currentPage}">
                                <input type="hidden" name="courseId" value="${course.id}">
                                <button type="submit" class="btn btn-outline-primary">${update}</button>
                            </form>
                        </td>
                        <td>
                            <form action="delete_course" class="text-center" method="GET">
                                <input type="hidden" name="courseName" value="${course.name}">
                                <input type="hidden" name="courseId" value="${course.id}">
                                <button type="submit" class="btn btn-outline-danger">${delete}</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <nav>
            <ul class="pagination">
                <c:if test="${currentPage == 1}">
                    <c:set var="prevDisabled" value="disabled"></c:set>
                </c:if>
                <li class="page-item ${prevDisabled}"><a class="page-link" href="manage_courses?page=${currentPage - 1}">${previous}</a></li>

                <c:forEach begin="1" end="${pageCount}" var="num" varStatus="loop">
                    <c:if test="${num == currentPage}">
                        <c:set var="active" value="active"></c:set>
                    </c:if>
                    <li class="page-item ${active}"><a class="page-link" href="manage_courses?page=${loop.index}">${loop.index}</a></li>
                    <c:set var="active" value="null"></c:set>
                </c:forEach>

                <c:if test="${currentPage == pageCount}">
                    <c:set var="nextDisabled" value="disabled"></c:set>
                </c:if>
                <li class="page-item ${nextDisabled}"><a class="page-link" href="manage_courses?page=${currentPage + 1}">${next}</a></li>
            </ul>
        </nav>
    </div>
    <!-- add theme modal -->
    <div class="modal fade" id="add-theme-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">${enter_theme_name}</h5>
                </div>
                <form action="manage_courses" method="POST">
                    <input type="hidden" name="command" value="addTheme">
                    <input type="hidden" name="page" value="${currentPage}">
                    <div class="modal-body">
                        <input class="add-theme-input" name="themeName" type="text"
                               value="${pageContext.request.getParameter("prevThemeName")}" required>
                        <div id="theme-exists-message" class="alert alert-danger mt-2 d-none" role="alert">
                            ${theme_exists}
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="close-add-theme" class="btn btn-secondary" data-bs-dismiss="modal">
                            ${close}
                        </button>
                        <button type="submit" class="btn btn-primary">${add}</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- add theme modal -->

    <!-- remove theme modal -->
    <div id="remove-theme-modal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">${remove_theme}</h5>
                </div>
                <form action="manage_courses" method="POST">
                    <input type="hidden" name="command" value="removeTheme">
                    <input type="hidden" name="page" value="${currentPage}">
                    <div class="modal-body">
                        <select name="themeId" class="form-select" id="delete-course-theme">
                            <c:forEach items="${themes}" var="theme">
                                <option value="${theme.id}">${theme.name}</option>
                            </c:forEach>
                        </select>
                    <div id="cant-delete-theme-message" class="cant-delete-theme-message alert alert-danger mt-2 d-none" role="alert">
                        ${cant_delete_theme}
                    </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="close-remove-theme" class="btn btn-secondary" data-bs-dismiss="modal">
                    ${close}
                </button>
                <button type="submit" class="btn btn-danger">${delete}</button>
            </div>
            </form>
        </div>
    </div>
    </div>
    <!-- remove theme modal -->

</main>
<%@include file="../footer.jsp"%>
</body>
</html>