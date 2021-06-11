<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<fmt:message key="role" var="role"/>
<fmt:message key="block" var="block"/>
<fmt:message key="unblock" var="unblock"/>
<body>
<main class="container mx-auto">
    <h1 class="text-center mt-3">${manage_students}</h1>
    <table class="table">
        <thead>
            <tr>
                <th>#</th>
                <th>${first_name}</th>
                <th>${last_name}</th>
                <th>${login}</th>
                <th>${role}</th>
                <th class="text-center">${action}</th>
            </tr>
        </thead>
        <tbody>

            <c:set var="count" value="0"/>
            <c:forEach items="${users}" var="user">
            <tr>
                <td>${count + 1}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.login}</td>
                <td>${user.role}</td>
                <form method="POST">
                    <input type="hidden" name="command" value="updateUserBlockedStatus">
                    <input type="hidden" name="userId" value="${user.id}">
                <c:if test="${user.blocked == false}">
                    <td class="text-center"><button type="submit" name="block" class="btn btn-danger" value="true">${block}</button></td>
                </c:if>
                <c:if test="${user.blocked == true}">
                    <td class="text-center"><button type="submit" name="block" class="btn btn-success" value="false">${unblock}</button></td>
                </c:if>
                </form>
            </tr>
            </c:forEach>
        </tbody>
    </table>
    <%@include file="../footer.jsp"%>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>
</html>
