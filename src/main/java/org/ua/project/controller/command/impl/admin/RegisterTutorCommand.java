package org.ua.project.controller.command.impl.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.authorization.RegistrationUtility;
import org.ua.project.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterTutorCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegisterTutorCommand.class);


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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

