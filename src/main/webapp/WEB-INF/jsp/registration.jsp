<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle" scope="page" value="Registration page"/>
<body id="reg_page">
<%@ include file="header.jsp" %>
<main>
    <c:set var="regResult" scope="page" value="${pageContext.request.getParameter(\"registrationResult\")}"/>
    <c:set var="prevFirstName" scope="page" value="${pageContext.request.getParameter(\"prevFirstName\")}"/>
    <c:set var="prevLastName" scope="page" value="${pageContext.request.getParameter(\"prevLastName\")}"/>
    <c:set var="prevLogin" scope="page" value="${pageContext.request.getParameter(\"prevLogin\")}"/>
    <c:if test="${\"success\" ne regResult}">
        <h3 class="text-center">${register}</h3>
        <%@include file="registration_form.jsp"%>
    </c:if>
    <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
        <symbol id="check-circle-fill" fill="currentColor" viewBox="0 0 16 16">
            <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"></path>
        </symbol>
        <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"></path>
        </symbol>
    </svg>
    <c:choose>
        <c:when test="${\"success\" eq regResult}">
            <div class="alert alert-success d-flex align-items-center" role="alert">
                <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Success:">
                    <use xlink:href="#check-circle-fill"/>
                </svg>
                <div>
                    ${registration_success}
                </div>
            </div>
        </c:when>
        <c:when test="${\"userExists\" eq regResult}">
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:">
                    <use xlink:href="#exclamation-triangle-fill"/>
                </svg>
                <div>
                    ${user_already_exists}
                </div>
            </div>
        </c:when>
        <c:when test="${\"invalidData\" eq regResult}">
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:">
                    <use xlink:href="#exclamation-triangle-fill"/>
                </svg>
                <div>
                    <c:if test="${pageContext.request.getParameter(\"invalid_firstName\") ne null}">
                        ${invalid_first_name}<br>
                    </c:if>
                    <c:if test="${pageContext.request.getParameter(\"invalid_lastName\") ne null}">
                        ${invalid_last_name}<br>
                    </c:if>
                    <c:if test="${pageContext.request.getParameter(\"invalid_login\") ne null}">
                        ${invalid_login}<br>
                    </c:if>
                    <c:if test="${pageContext.request.getParameter(\"invalid_password\") ne null}">
                        ${invalid_password}<br>
                    </c:if>
                </div>
            </div>
        </c:when>
    </c:choose>
</main>
<%@include file="footer.jsp"%>
</body>
</html>