<table class="table table-bordered border-primary ">
    <thead>
    <tr>
        <th>#</th>
        <th>Name</th>
        <th>Theme</th>
        <th>Start date</th>
        <th>End date</th>
        <th>Description</th>
        <th>Tutor login</th>
        <th>Tutor first name</th>
        <th>Tutor last name</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:set var="currentPage" value="${pageContext.request.getParameter(\"page\")}"/>
    <c:set var="count" value="${5 * (currentPage - 1)}"></c:set>
    <c:forEach items="${coursesPage}" var="course">
        <tr>
            <c:set var="count" value="${count + 1}"></c:set>
            <td>${count}</td>
            <td>${course.name}</td>
            <td>${course.theme.name}</td>
            <td>${course.startDate}</td>
            <td>${course.endDate}</td>
            <td>${course.description}</td>
            <td>${course.tutor.login}</td>
            <td>${course.tutor.firstName}</td>
            <td>${course.tutor.lastName}</td>
            <td>
                <form action="subscribe" class="text-center" method="POST">
                    <input type="hidden" name="page" value="${currentPage}">
                    <input type="hidden" name="courseId" value="${course.id}">
                    <button type="submit" class="btn btn-outline-info">Enroll</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
