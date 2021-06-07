<footer class="container mt-3">
    <hr class="featurette-divider"/>
    &copy; 2021 Company, Inc.
    <div style="float:right;" class="display-inline">
        <form action="${pageContext.request.contextPath}/main_page" class="localization-form">
            <input type="hidden" name="command" value="switchLocale">
            <button type="submit" name="locale" value="en">En</button>
            <button type="submit" name="locale" value="ru">Ru</button>
        </form>

    </div>
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/static/js/app.js"></script>
</footer>