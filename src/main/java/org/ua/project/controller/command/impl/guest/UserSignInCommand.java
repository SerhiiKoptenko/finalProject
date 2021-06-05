package org.ua.project.controller.command.impl.guest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.authorization.AuthorizationUtility;
import org.ua.project.controller.util.validation.ValidationResult;
import org.ua.project.controller.util.validation.Validator;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.service.UserService;
import org.ua.project.model.service.exception.WrongPasswordException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class UserSignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UserSignInCommand.class);

    private static final String GO_TO_MAIN_PAGE = "/index.jsp";
    private static final String GO_TO_LOGIN_PAGE = "/sign_in_page?";

    public static final String LOGIN_FAILED_USER_BLOCKED = "signInError=userBlocked";
    private static final String LOGIN_FAILED_INVALID_DATA = "signInError=invalidData";
    private static final String LOGIN_FAILED_ALREADY_SIGNED_IN = "signInError=alreadySignedIn";
    private static final String LOGIN_FAILED_NO_SUCH_USER = "signInError=wrongUserOrPassword";
    private static final String PREV_LOGIN = "&" + Parameter.LOGIN.getValue() + "=";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String errorUrl = ControllerConstants.REDIRECT_PREFIX + GO_TO_LOGIN_PAGE;

        final String LOGIN_PARAMETER = Parameter.LOGIN.getValue();
        final String PASSWORD_PARAMETER = Parameter.PASSWORD.getValue();

        String login = Optional.ofNullable(req.getParameter(LOGIN_PARAMETER)).orElse("");
        String password = Optional.ofNullable(req.getParameter(PASSWORD_PARAMETER)).orElse("");

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

        if (!AuthorizationUtility.signInUser(req, user.getLogin())) {
            errorUrl += LOGIN_FAILED_ALREADY_SIGNED_IN;
            errorUrl += PREV_LOGIN + login;
            return errorUrl;
        }

        AuthorizationUtility.saveUserToSession(req, user);
        logger.trace("User successfully signed in.");
        return GO_TO_MAIN_PAGE;
    }
}
