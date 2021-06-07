<%@ tag body-content="empty" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="date" required="true" type="java.time.LocalDate" %>
<c:set var="locale" value="${sessionScope.locale}"/>

<c:set var="appPattern" value="yyyy-MM-dd"/>
<c:choose>
    <c:when test="${locale eq 'ru'}">
        <c:set var="pattern" value="dd/MM/yyyy"/>
    </c:when>
    <c:otherwise>
        <c:set var="pattern" value="MM/dd/yyyy"/>
    </c:otherwise>
</c:choose>

<fmt:parseDate value="${date}" pattern="${appPattern}" var="parsedDate" type="date"/>
<fmt:formatDate value="${parsedDate}" type="date" pattern="${pattern}"/>