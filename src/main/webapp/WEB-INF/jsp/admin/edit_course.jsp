<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="page" value="Manage courses"/>
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
<fmt:message key="edit_course" var="edit_course"/>
<fmt:message key="update_success" var="update_success"/>
<main class="container mx-auto">
    <form id="edit-course-form" action="edit_course" method="POST">
        <input type="hidden" name="page" value="${pageContext.request.getParameter("page")}">
        <input type="hidden" name="command" value="updateCourse">
        <input type="hidden" name="courseId" value="${editedCourse.id}">
        <div class="mb-2">
            <h1 class="text-center mt-3">${edit_course}</h1>

            <label for="course-name" class="mt-5 mx-2 py-1">${course_name}:</label>
            <div class="input-group mb-2">
                <input id="course-name" name="courseName" type="text" class="form-control" value="${editedCourse.name}">
            </div>

            <label for="course-theme" class="mx-2 py-1">${theme_name} </label>
            <div class="input-group mb-2 ">
                <select name="themeId" class="form-select" id="course-theme">
                    <option selected value="${editedCourse.theme.id}">${editedCourse.theme.name}</option>
                    <c:forEach items="${themes}" var="theme">
                        <option value="${theme.id}">${theme.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        </div>

        <label for="start-date" class="mx-2 py-1 ">${start_date}:</label>
        <div class="input-group mb-2">
            <input id="start-date" name="startDate" type="date" class="form-control" value="${editedCourse.startDate}">
        </div>

        <label for="end-date" class="mx-2 py-1 ">${end_date}:</label>
        <div class="input-group mb-2">
            <input id="end-date" name="endDate" type="date" class="form-control" value="${editedCourse.endDate}">
        </div>


        <c:set var="isTutorSet" value="${editedCourse.tutor ne null}"/>
        <label for="tutor" class="mx-2 py-1">${add_tutor}: </label>
        <div class="input-group mb-2 ">
            <select name="tutorId" class="form-select" id="tutor">
                    <c:if test="${isTutorSet}">$
                <option value="${edit_course.tutor.id}"
                        selected>${editedCourse.tutor.firstName} ${editedCourse.tutor.lastName}
                    (${editedCourse.tutor.login})
                </option>
                </c:if>
                </option>
                <option <c:if test="${!isTutorSet}>">selected</c:if> value="">No tutor</option>
                <c:forEach items="${tutors}" var="tutor">
                    <option value="${tutor.id}">${tutor.firstName} ${tutor.lastName} (${tutor.login})</option>
                </c:forEach>
            </select>
        </div>
        <label for="description" class="mx-2 py-1">${course_description}: </label>
        <div class="input-group ">
            <textarea id="description" name="description" class="form-control">${editedCourse.description}</textarea>
        </div>
        <button type="submit" class="btn btn-primary col-lg-2 offset-lg-5 mt-2 mb-2">${update}</button>
    </form>
    <c:if test="${pageContext.request.getParameter(\"result\") eq \"errorInvalidData\"}">
        <div class="alert alert-danger mt-2 mb-2" role="alert">
            <c:if test="${pageContext.request.getParameter(\"invalid_courseName\") ne null}">
                ${invalid_course_name}.<br>
            </c:if>
            <c:if test="${pageContext.request.getParameter(\"invalid_startDateOrEndDate\") ne null}">
                ${invalid_course_date}.<br>
            </c:if>
        </div>
    </c:if>
    <c:if test="${pageContext.request.getParameter(\"result\") eq \"success\"}">
        <div class="alert alert-success mt-2 mb-2" role="alert">
                ${update_success}.
        </div>
    </c:if>
</main>
<%@include file="../footer.jsp" %>
</body>
</html>