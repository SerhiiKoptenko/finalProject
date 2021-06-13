<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<c:set var="pageTitle" scope="page" value="Manage courses"/>
<%@ include file="WEB-INF/jsp/header.jsp"%>
<fmt:message key="error404" var="error404"/>
<main class="text-center">
    <h1 class="mt-5 text-danger">${error404}</h1>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>

</html>