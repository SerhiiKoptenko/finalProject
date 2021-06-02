<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <%@ include file="../header.jsp"%>
    <main class="container">
        <div class="alert alert-success" role="alert">
            <p>You have successfully enrolled in course ${pageContext.request.getParameter("courseName")}</p>
            <hr>
            <a href="../main_page">Go back to available courses.</a>
        </div>
    </main>
</html>