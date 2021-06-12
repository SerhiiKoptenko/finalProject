<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="WEB-INF/jsp/header.jsp"%>
<c:set var="pageTitle" scope="page" value="Error"/>
<fmt:message key="${error}" var="errorMessage"/>
<fmt:message key="${unexpected_error}" var="unexpected_error"/>
<main class="text-center">
  <h1 class="mt-5 text-danger">
    <c:choose>
      <c:when test="${error ne null}">Error: ${errorMessage}</c:when>
      <c:otherwise>
        ${unexpected_error}
      </c:otherwise>
    </c:choose>
  </h1>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>

</html>