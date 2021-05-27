package org.ua.project.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.authorization.AuthorizationUtility;
import org.ua.project.controller.util.validation.ValidationResult;
import org.ua.project.controller.util.validation.Validator;
import org.ua.project.model.dto.SignInData;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.service.UserService;
import org.ua.project.service.exception.NoSuchUserException;
import org.ua.project.service.exception.ServiceException;
import org.ua.project.service.exception.WrongPasswordException;
import org.ua.project.service.util.encryption.EncryptionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class UserSignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UserSignInCommand.class);

    private static final String GO_TO_MAIN_PAGE = "/index.jsp";
    private static final String GO_TO_LOGIN_PAGE = "/sign_in_page?";

    private static final String LOGIN_FAILED_INVALID_DATA = "signInError=invalidData";
    private static final String LOGIN_FAILED_ALREADY_SIGNED_IN = "signInError=alreadySignedIn";
    private static final String LOGIN_FAILED_NO_SUCH_USER = "signInError=wrongUserOrPassword";
    private static final String PREV_LOGIN = "&" + Parameter.LOGIN.getValue() + "=";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String url = ControllerConstants.REDIRECT_PREFIX;

        final String LOGIN_PARAMETER = Parameter.LOGIN.getValue();
        final String PASSWORD_PARAMETER = Parameter.PASSWORD.getValue();

        String login = Optional.ofNullable(req.getParameter(LOGIN_PARAMETER)).orElse("");
        String password = Optional.ofNullable(req.getParameter(PASSWORD_PARAMETER)).orElse("");

        Validator validator = Validator.getInstance();
        ValidationResult result =  validator.validateSignInData(login, password);
        if (!result.isSuccessful()) {
            url += GO_TO_LOGIN_PAGE;
            url += LOGIN_FAILED_INVALID_DATA;
            url += PREV_LOGIN + login;
            url += result.getInvalidParametersString();
            return url;
        }

        UserService service = new UserService();
        User user = null;
        try {
           user = service.signInUser(login, password);
        } catch (EntityNotFoundException | WrongPasswordException e) {
            url += GO_TO_LOGIN_PAGE;
            url += LOGIN_FAILED_NO_SUCH_USER;
            url += PREV_LOGIN + login;
            return url;
        }

        if (!AuthorizationUtility.signInUser(req, user.getLogin())) {
            url += GO_TO_LOGIN_PAGE;
            url += LOGIN_FAILED_ALREADY_SIGNED_IN;
            url += PREV_LOGIN + login;
            return url;
        }

        AuthorizationUtility.assignRole(req, user.getLogin(), user.getRole());
        logger.trace("User successfully signed in.");
        return url + GO_TO_MAIN_PAGE;
    }
}
