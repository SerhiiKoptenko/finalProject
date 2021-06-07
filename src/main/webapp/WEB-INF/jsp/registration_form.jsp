<fmt:message key="registration_success" var="registration_success"/>
<fmt:message key="user_already_exists" var="user_already_exists"/>
<fmt:message key="invalid_first_name" var="invalid_first_name"/>
<fmt:message key="invalid_last_name" var="invalid_last_name"/>
<fmt:message key="registration" var="registration"/>
<div class="registrationForm container mx-auto p-5 rounded ">
    <h3 class="text-center">${registration}</h3>
    <form action="registration_page" method="post">
        <input type="hidden" name="command" value="register">
        <div class="mb-3">
            <label for="firstName" class="form-label ">${first_name}</label>
            <input type="text" class="form-control" name="firstName" id="firstName" required
                   value="${prevFirstName}">
        </div>
        <div class="mb-3">
            <label for="lastName" class="form-label">${last_name}</label>
            <input type="text" class="form-control" name="lastName" id="lastName" required
                   value="${prevLastName}">
        </div>
        <div class="mb-3">
            <label for="login" class="form-label">${login}</label>
            <input type="text" class="form-control" name="login" id="login" required
                   value="${prevLogin}">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">${password}</label>
            <input type="password" class="form-control" name="password" id="password" required
            >
        </div>
        <button type="submit" class="btn btn-primary">${submit}</button>
    </form>
</div>