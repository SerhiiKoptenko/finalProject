<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" scope="page" value="Sign in page"/>
<body id="sign_in_page">
<%@ include file="header.jsp" %>
<main>
    <c:set var="signInError" scope="page" value="${pageContext.request.getParameter(\"signInError\")}"/>
    <c:set var="prevLogin" scope="page" value="${pageContext.request.getParameter(\"login\")}"/>
    <c:set var="invalidLogin" scope="page" value="${pageContext.request.getParameter(\"invalid_login\")}"/>
    <c:set var="invalidPassword" scope="page" value="${pageContext.request.getParameter(\"invalid_password\")}"/>
    <!--Login form-->
    <div class="container mx-auto p-5 rounded">
        <h3 class="text-center">Sign in</h3>
        <form action="signIn_page" method="post">
            <input type="hidden" name="command" value="signIn">
            <div class="mb-3">
                <label for="login" class="form-label">Login</label>
                <input type="text" class="form-control" name="login" id="login" required value="${prevLogin}">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" name="password" id="password" required>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
    <!--/Login form-->
    <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
        <symbol id="check-circle-fill" fill="currentColor" viewBox="0 0 16 16">
            <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"></path>
        </symbol>
        <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"></path>
        </symbol>
    </svg>
    <c:if test="${signInError ne null}">
        <div class="alert alert-danger d-flex align-items-center" role="alert">
            <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:">
                <use xlink:href="#exclamation-triangle-fill"/>
            </svg>
            <div>
               <c:choose>
                   <c:when test="${signInError eq \"invalidData\"}">
                       <c:if test="${invalidLogin ne null}">
                           Invalid login.<br>
                       </c:if>
                       <c:if test="${invalidPassword ne null}">
                           Invalid password.<br>
                       </c:if>
                   </c:when>
                   <c:when test="${signInError eq \"wrongUserOrPassword\"}">
                       Wrong login or password.<br>
                   </c:when>
                   <c:when test="${signInError eq \"alreadySignedIn\"}">
                       User ${prevLogin} already signed in.<br>
                   </c:when>
               </c:choose>
            </div>
        </div>
    </c:if>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
</body>

</html>