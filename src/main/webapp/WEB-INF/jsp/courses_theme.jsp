<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" scope="page" value="Main page"/>
<%@ include file="header.jsp"%>
<body>
<main class="container mx-auto">
    <h2 class="text-center">Available courses</h2>
    <div class="row">
        <div class="p-1 ">
            <h4>Displaying courses by theme: ${theme.name}</h4>
            <%@include file="courses_view.jsp"%>
            <nav>
                <c:set var="sortOption" value="${pageContext.request.getParameter(\"sortOption\")}"/>
                <ul class="pagination">
                    <c:if test="${currentPage == 1}">
                        <c:set var="prevDisabled" value="disabled"></c:set>
                    </c:if>
                    <li class="page-item ${prevDisabled}"><a class="page-link" href="courses_theme?page=${currentPage - 1}&themeId=${theme.id}">Previous</a></li>

                    <c:forEach begin="1" end="${pageCount}" var="num" varStatus="loop">
                        <c:if test="${num == currentPage}">
                            <c:set var="active" value="active"></c:set>
                        </c:if>
                        <li class="page-item ${active}"><a class="page-link" href="courses_theme?page=${loop.index}&themeId=${theme.id}">${loop.index}</a></li>
                        <c:set var="active" value="null"></c:set>
                    </c:forEach>

                    <c:if test="${currentPage == pageCount}">
                        <c:set var="nextDisabled" value="disabled"></c:set>
                    </c:if>
                    <li class="page-item ${nextDisabled}"><a class="page-link" href="courses_theme?page=${currentPage + 1}&themeId=${theme.id}">Next</a></li>
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