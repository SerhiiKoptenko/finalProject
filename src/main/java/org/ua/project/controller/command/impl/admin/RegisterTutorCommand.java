package org.ua.project.controller.command.impl.admin;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.authorization.RegistrationUtility;
import org.ua.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command which attempts to register new tutor.
 */
public class RegisterTutorCommand implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String redirectUrl = ControllerConstants.REDIRECT_TO_REGISTER_TUTOR_PAGE + "?";

        String firstName = req.getParameter(Parameter.FIRST_NAME.getValue());
        String lastName = req.getParameter(Parameter.LAST_NAME.getValue());
        String login = req.getParameter(Parameter.LOGIN.getValue());
        String password = req.getParameter(Parameter.PASSWORD.getValue());

        User user = new User.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setLogin(login)
                .setRole(User.Role.TUTOR)
                .build();


        return RegistrationUtility.registerUser(user, redirectUrl);
    }
}

