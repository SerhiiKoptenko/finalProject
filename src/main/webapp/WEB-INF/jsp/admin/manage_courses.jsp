<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" scope="page" value="Manage courses"/>
<%@ include file="../header.jsp" %>
<body>
<main class="container mx-auto">
    <div class="row">
        <form id="add-course-form" class="d-none">
            <button id="hide-add-course" type="button" class="btn-close mb-5  mt-1 d-block" style="float: right;"
                    aria-label="Close"></button>
            <div class="mb-2">
                <label for="course-theme" class="mt-5 mx-2 py-1">Course theme: </label>
                <div class="input-group mb-2 ">
                    <select class="form-select" id="course-theme">
                        <c:forEach items="${themes}" var="theme">
                            <option value="${theme.name}">${theme.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <label for="course-name" class="mx-2 py-1">Course name:</label>
            <div class="input-group mb-2">
                <input id="course-name" type="text" class="form-control">
            </div>

            <label for="start-date" class="mx-2 py-1 ">Start date:</label>
            <div class="input-group mb-2">
                <input id="start-date" type="date" class="form-control">
            </div>

            <label for="end-date" class="mx-2 py-1 ">End date:</label>
            <div class="input-group mb-2">
                <input id="end-date" type="date" class="form-control">
            </div>

            <label for="tutor" class="mx-2 py-1">Tutor: </label>
            <div class="input-group mb-2 ">
                <select class="form-select" id="tutor">
                    <option value="none" selected>None</option>
                    <c:forEach items="${tutors}" var ="tutor">
                        <option value="${tutor.id}">${tutor.firstName} ${tutor.lastName}</option>
                    </c:forEach>
                </select>
            </div>
            <label for="description" class="mx-2 py-1">Course description: </label>
            <div class="input-group ">
                <textarea id="description" class="form-control"></textarea>
            </div>
            <button type="submit" class="btn btn-success col-lg-2 offset-lg-5 mt-2 mb-2">Add</button>
        </form>
        <button id="disp-add-course" type="button " class="btn btn-outline-success mx-2 mt-1 p-1 col-lg-2 col-md-2 ">
            <svg xmlns="http://www.w3.org/2000/svg" width="16 " height="16 " fill="currentColor "
                 class="bi bi-plus-circle " viewBox="0 0 16 16 ">
                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z "/>
                <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z "/>
            </svg>
            Add new course
        </button>
        <div class="p-1 ">
            <table class="table table-bordered border-primary ">
                <thead>
                <tr>
                    <th scope="col ">id</th>
                    <th scope="col ">Name</th>
                    <th scope="col ">Theme</th>
                    <th scope="col ">Start date</th>
                    <th scope="col ">End date</th>
                    <th scope="col ">Description</th>
                    <th scope="col ">Tutor login</th>
                    <th scope="col ">Tutor first name</th>
                    <th scope="col ">Tutor last name</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${courses}" var="course">
                <tr>
                    <td>${course.id}</td>
                    <td>${course.name}</td>
                    <td>${course.theme.name}</td>
                    <td>${course.startDate}</td>
                    <td>${course.endDate}</td>
                    <td>${course.description}</td>
                    <td>${course.tutor.login}</td>
                    <td>${course.tutor.firstName}</td>
                    <td>${course.tutor.lastName}</td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>
<script src="static/js/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js " integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4 " crossorigin="anonymous "></script>
<script src="${pageContext.request.contextPath}/app.js"></script>
</body>
</html>