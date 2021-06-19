package org.ua.project.controller.command.impl.guest;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.RegistrationUtility;
import org.ua.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Command which attempts to register new student.
 */
public class StudentRegistrationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String redirectUrl = ControllerConstants.REDIRECT_TO_REGISTRATION_PAGE + "?";
        redirectUrl += Optional.ofNullable(req.getQueryString()).orElse("");

        String firstName = req.getParameter(Parameter.FIRST_NAME.getValue());
        String lastName = req.getParameter(Parameter.LAST_NAME.getValue());
        String login = req.getParameter(Parameter.LOGIN.getValue());
        String password = req.getParameter(Parameter.PASSWORD.getValue());

        User user = new User.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setLogin(login)
                .setRole(User.Role.STUDENT)
                .build();

        redirectUrl = RegistrationUtility.registerUser(user, redirectUrl);
        return redirectUrl;
    }
}
