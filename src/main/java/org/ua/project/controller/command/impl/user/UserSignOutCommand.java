package org.ua.project.controller.command.impl.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.util.authorization.AuthorizationUtility;
import org.ua.project.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class UserSignOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UserSignOutCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = Optional.ofNullable((User) req.getSession().getAttribute(ControllerConstants.USER_ATTR))
                .orElseThrow(IllegalStateException::new);
        logger.info("Signing out user {}", user);
        AuthorizationUtility.signOutUser(req, user.getLogin());
        logger.info("User {} has been successfully signed out", user);
       return ControllerConstants.REDIRECT_TO_MAIN_PAGE;
    }
}
