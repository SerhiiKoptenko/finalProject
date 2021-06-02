<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="cust" uri="/WEB-INF/tags.tld"%>
<% request.setAttribute("date1", LocalDate.of(1990, 3, 15));%>
<% request.setAttribute("date2", LocalDate.of(2033, 3, 15));%>
<cust:isDateAfterToday date="${date1}"></cust:isDateAfterToday>
<cust:isDateAfterToday date="${date2}"></cust:isDateAfterToday>