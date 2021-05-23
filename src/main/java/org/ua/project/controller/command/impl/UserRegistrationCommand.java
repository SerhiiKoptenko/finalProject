package org.ua.project.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.validation.ValidationResult;
import org.ua.project.controller.validation.Validator;
import org.ua.project.model.dto.RegistrationData;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.service.ServiceContainer;
import org.ua.project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserRegistrationCommand implements Command {
    Logger logger = LogManager.getLogger(UserRegistrationCommand.class);

    private static final String REG_RESULT = "&registrationResult=";
    private static final String REG_SUCCESS = "success";
    private static final String REG_FAILED_USER_EXISTS = "userExists";
    private static final String REG_FAILED_INVALID_DATA = "invalidData";
    private static final String GO_TO_REG_PAGE = "/registration_page?";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String redirectUrl = ControllerConstants.REDIRECT_PREFIX + GO_TO_REG_PAGE + REG_RESULT;

        String firstName = req.getParameter(Parameter.FIRST_NAME);
        String lastName = req.getParameter(Parameter.LAST_NAME);
        String login = req.getParameter(Parameter.LOGIN);
        String password = req.getParameter(Parameter.PASSWORD);

        RegistrationData data = new RegistrationData();
        data.setFirstName(firstName);
        data.setLastName(lastName);
        data.setLogin(login);
        data.setPassword(password);

        logger.trace("Attempting to register user with following data: " + data);
        Validator validator = Validator.getInstance();
        ValidationResult validationResult = validator.validateRegData(data);
        if (!validationResult.isSuccessful()) {
            logger.trace("Validation failed.");
            redirectUrl += REG_FAILED_INVALID_DATA;
            redirectUrl = includePreviousValues(redirectUrl, data);
            List<String> invalidParameters = validationResult.getInvalidParameters();
            StringBuilder sb = new StringBuilder(redirectUrl);
            for (String parameter : invalidParameters) {
                sb.append("&invalidParameter=").append(parameter);
            }
            return sb.toString();
        }

        UserService userService = ServiceContainer.getUserService();

        try {
            userService.registerUser(data);
            redirectUrl += REG_SUCCESS;
            logger.trace("User successfully registered.");
        } catch (EntityAlreadyExistsException e) {
            redirectUrl += REG_FAILED_USER_EXISTS;
            redirectUrl = includePreviousValues(redirectUrl, data);
            logger.trace("Registration failed: user already exists.");
        } catch (DBException e) {
            //TODO: handle
        }
        return redirectUrl;
    }

    private String includePreviousValues(String redirectUrl, RegistrationData data) {
        redirectUrl += "&firstName=" + data.getFirstName();
        redirectUrl += "&login=" + data.getLogin();
        redirectUrl += "&lastName=" + data.getLastName();
        return redirectUrl;
    }
}
