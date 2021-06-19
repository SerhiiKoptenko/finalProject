package org.ua.project.controller.command.impl.guest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.validation.ValidationResult;
import org.ua.project.controller.util.validation.Validator;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.service.UserService;
import org.ua.project.model.service.exception.WrongPasswordException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;

/**
 * Command which attempts to sign in user.
 */
public class UserSignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UserSignInCommand.class);

    public static final String LOGIN_FAILED_USER_BLOCKED = "signInError=userBlocked";
    private static final String LOGIN_FAILED_INVALID_DATA = "signInError=invalidData";
    private static final String LOGIN_FAILED_ALREADY_SIGNED_IN = "signInError=alreadySignedIn";
    private static final String LOGIN_FAILED_NO_SUCH_USER = "signInError=wrongUserOrPassword";
    private static final String PREV_LOGIN = "&" + Parameter.LOGIN.getValue() + "=";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String errorUrl = ControllerConstants.REDIRECT_TO_SIGN_IN_PAGE + "?";

        String login = Optional.ofNullable(req.getParameter(Parameter.LOGIN.getValue())).orElse("");
        String password = Optional.ofNullable(req.getParameter(Parameter.PASSWORD.getValue())).orElse("");

        Validator validator = Validator.getInstance();
        ValidationResult result =  validator.validateSignInData(login, password);
        if (!result.isSuccessful()) {
            errorUrl += LOGIN_FAILED_INVALID_DATA;
            errorUrl += PREV_LOGIN + login;
            errorUrl += result.getInvalidParametersString();
            return errorUrl;
        }

        UserService service = new UserService();
        User user;
        try {
           user = service.signInUser(login, password);
        } catch (EntityNotFoundException | WrongPasswordException e) {
            errorUrl += LOGIN_FAILED_NO_SUCH_USER;
            errorUrl += PREV_LOGIN + login;
            return errorUrl;
        }

        if (user.isBlocked()) {
            errorUrl += LOGIN_FAILED_USER_BLOCKED;
            return errorUrl;
        }

        Set<String> users = (Set<String>) req.getSession().getServletContext().getAttribute(ControllerConstants.LOGGED_USERS_ATTR);
        users.add(user.getLogin());
        if (!users.add(user.getLogin())) {
            errorUrl += LOGIN_FAILED_ALREADY_SIGNED_IN;
            errorUrl += PREV_LOGIN + login;
            return errorUrl;
        }

        req.getSession().setAttribute(ControllerConstants.USER_ATTR, user);
        logger.info("User {} successfully signed in.", user.getLogin());

        if (User.Role.TUTOR.equals(user.getRole())) {
            return ControllerConstants.REDIRECT_TO_PERSONAL_CABINET ;
        }

        if (User.Role.ADMIN.equals(user.getRole())) {
            return ControllerConstants.REDIRECT_TO_MANAGE_COURSES_PAGE;
        }

        return ControllerConstants.REDIRECT_TO_MAIN_PAGE;
    }
}
