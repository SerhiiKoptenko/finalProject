package org.ua.project.controller.command.impl.guest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.authorization.RegistrationUtility;
import org.ua.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class UserRegistrationCommand implements Command {
    private static Logger logger = LogManager.getLogger(UserRegistrationCommand.class);

    private static final String GO_TO_REG_PAGE = "/registration_page?";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectUrl = ControllerConstants.REDIRECT_PREFIX + GO_TO_REG_PAGE;
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
