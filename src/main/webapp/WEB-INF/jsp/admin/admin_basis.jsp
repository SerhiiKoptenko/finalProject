<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" scope="page" value="Admin basis"/>
<%@ include file="../header.jsp"%>
<body>
<main class="container mx-auto">
    <div class="row justify-content-center">
        <div class="col-lg-3 border border-3 border-primary rounded p-2 m-2">
            <div class="text-center"><a href="manage_courses" class="action-button btn btn-primary align-middle">Manage courses</a></div>
            <div class="text-center">
                <p>Create, delete or edit courses.</p>
            </div>
        </div>
        <div class="col-lg-3 border border-3 border-primary rounded p-2 m-2">
            <div class="text-center"><a href="#" class="action-button btn btn-primary align-middle">Manage students</a></div>
            <div class="text-center">
                <p>Block/unblock students.</p>
            </div>
        </div>
        <div class="col-lg-3 border border-3 border-primary rounded p-2 m-2">
            <div class="text-center"><a href="#" class="action-button btn btn-primary align-middle">Manage tutors.</a></div>
            <div class="text-center">
                <p>Register and assign tutors.</p>
            </div>
        </div>
    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>

</html>
