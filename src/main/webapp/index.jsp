<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${user.role eq 'GUEST' || user.role eq 'STUDENT'}"><c:redirect url="main_page"/></c:if>
<c:if test="${user.role eq 'TUTOR'}"><c:redirect url="user/personal_cabinet"/></c:if>
<c:if test="${user.role eq 'ADMIN'}"><c:redirect url="admin/manage_courses"/></c:if>
