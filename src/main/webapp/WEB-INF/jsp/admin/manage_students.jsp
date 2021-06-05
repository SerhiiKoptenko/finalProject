<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%@ include file="../header.jsp" %>
<body>
<main class="container mx-auto">
    <h1 class="text-center mt-3">Manage students</h1>
    <table class="table">
        <thead>
            <tr>
                <th>#</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Login</th>
                <th>Role</th>
                <th class="text-center">Action</th>
            </tr>
        </thead>
        <tbody>

            <c:set var="count" value="0"/>
            <c:forEach items="${users}" var="user">
            <tr>
                <td>${count}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.login}</td>
                <td>${user.role}</td>
                <form method="POST">
                    <input type="hidden" name="command" value="updateUserBlockedStatus">
                    <input type="hidden" name="userId" value="${user.id}">
                <c:if test="${user.blocked == false}">
                    <td class="text-center"><button type="submit" name="block" class="btn btn-danger" value="true">Block</button></td>
                </c:if>
                <c:if test="${user.blocked == true}">
                    <td class="text-center"><button type="submit" name="block" class="btn btn-success" value="false">Unblock</button></td>
                </c:if>
                </form>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>

</html>
