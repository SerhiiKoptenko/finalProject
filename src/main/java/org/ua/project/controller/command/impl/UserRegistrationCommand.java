package org.ua.project.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.validation.ValidationResult;
import org.ua.project.controller.util.validation.Validator;
import org.ua.project.model.entity.User;
import org.ua.project.model.service.UserService;
import org.ua.project.model.service.exception.ServiceException;
import org.ua.project.model.service.exception.UserAlreadyExistsException;
import org.ua.project.model.service.util.encryption.EncryptionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class UserRegistrationCommand implements Command {
    private static Logger logger = LogManager.getLogger(UserRegistrationCommand.class);

    private static final String REG_SUCCESS = "&registrationResult=success";
    private static final String REG_FAILED_USER_EXISTS = "&registrationResult=userExists";
    private static final String REG_FAILED_INVALID_DATA = "&registrationResult=invalidData";
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
                .build();

        logger.trace("Attempting to register user " + user.getLogin());
        Validator validator = Validator.getInstance();
        ValidationResult validationResult = validator.validateUser(user);
        if (!validationResult.isSuccessful()) {
            logger.trace("Validation failed.");
            redirectUrl += REG_FAILED_INVALID_DATA;
            redirectUrl = includePreviousValues(redirectUrl, user);
            redirectUrl += validationResult.getInvalidParametersString();
            return redirectUrl;
        }

        user.setPassword(EncryptionUtil.encrypt(password));

        try {
            UserService userService = new UserService();
            userService.addUser(user);
            redirectUrl += REG_SUCCESS;
            logger.trace("User successfully registered.");
        } catch (UserAlreadyExistsException e) {
            redirectUrl += REG_FAILED_USER_EXISTS;
            redirectUrl = includePreviousValues(redirectUrl, user);
            logger.trace("Registration failed: user already exists.");
        } catch (ServiceException e) {
            //TODO: handle
        }
        return redirectUrl;
    }

    private String includePreviousValues(String redirectUrl, User user) {
        redirectUrl += "&firstName=" + user.getFirstName();
        redirectUrl += "&login=" + user.getLogin();
        redirectUrl += "&lastName=" + user.getLastName();
        return redirectUrl;
    }
}
