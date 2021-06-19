package org.ua.project.controller.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.util.validation.ValidationResult;
import org.ua.project.controller.util.validation.Validator;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.model.service.UserService;

/**
 * Utility class for user registration.
 */
public class RegistrationUtility {
    private static final Logger logger = LogManager.getLogger(RegistrationUtility.class);
    private static final String REG_SUCCESS = "registrationResult=success";
    private static final String REG_FAILED_USER_EXISTS = "&registrationResult=userExists";
    private static final String REG_FAILED_INVALID_DATA = "&registrationResult=invalidData";

    //suppress default constructor
    private RegistrationUtility(){
        throw new AssertionError();
    }

    /**
     * Attempts to register user. Appends registration result and invalid parameters (if any) to redirect url.
     * @param user - user to be registered.
     * @param redirectUrl - url to append registration result to.
     * @return redirectUrl with appended registration result.
     */
    public static String registerUser(User user, String redirectUrl) {
        logger.info("Attempting to register user {}", user.getLogin());
        Validator validator = Validator.getInstance();
        ValidationResult validationResult = validator.validateUser(user);
        if (!validationResult.isSuccessful()) {
            logger.info("Validation failed.");
            redirectUrl += REG_FAILED_INVALID_DATA;
            redirectUrl = includePreviousValues(redirectUrl, user);
            redirectUrl += validationResult.getInvalidParametersString();
            return redirectUrl;
        }

        try {
            UserService userService = new UserService();
            userService.registerUser(user);
        } catch (EntityAlreadyExistsException e) {
            redirectUrl += REG_FAILED_USER_EXISTS;
            redirectUrl = includePreviousValues(redirectUrl, user);
            logger.info("Registration failed: user already exists.");
            return redirectUrl;
        }

        redirectUrl += REG_SUCCESS;
        logger.info("User successfully registered.");
        return redirectUrl;
    }

    private static String includePreviousValues(String redirectUrl, User user) {
        redirectUrl += "&prevFirstName=" + user.getFirstName();
        redirectUrl += "&prevLogin=" + user.getLogin();
        redirectUrl += "&prevLastName=" + user.getLastName();
        return redirectUrl;
    }
}
