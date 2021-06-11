package org.ua.project.controller.util.authorization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.util.validation.ValidationResult;
import org.ua.project.controller.util.validation.Validator;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.model.service.UserService;
import org.ua.project.model.service.util.encryption.EncryptionUtil;

public class RegistrationUtility {
    private static final Logger logger = LogManager.getLogger(RegistrationUtility.class);
    private static final String REG_SUCCESS = "registrationResult=success";
    private static final String REG_FAILED_USER_EXISTS = "&registrationResult=userExists";
    private static final String REG_FAILED_INVALID_DATA = "&registrationResult=invalidData";

    //suppress default constructor
    private RegistrationUtility(){
        throw new AssertionError();
    }

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

        user.setPassword(EncryptionUtil.encrypt(user.getPassword()));

        try {
            UserService userService = new UserService();
            userService.addUser(user);
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
