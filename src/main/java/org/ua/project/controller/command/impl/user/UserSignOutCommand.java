package org.ua.project.controller.command.impl.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Set;

/**
 * Command which attempts to sign out user.
 */
public class UserSignOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UserSignOutCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Optional<User> userOpt = Optional.ofNullable((User) req.getSession().getAttribute(ControllerConstants.USER_ATTR));
        if (!userOpt.isPresent()) {
            return ControllerConstants.REDIRECT_TO_SIGN_IN_PAGE;
        }
        User user = userOpt.get();
        logger.info("Signing out user {}", user);
        signOutUser(req, user.getLogin());
        logger.info("User {} has been successfully signed out", user);
       return ControllerConstants.REDIRECT_TO_MAIN_PAGE;
    }

    public static boolean signOutUser(HttpServletRequest req, String userLogin) {
        HttpSession session = req.getSession();
        session.invalidate();
        Set<String> loggedUsers = (Set<String>) session.getServletContext().getAttribute(ControllerConstants.LOGGED_USERS_ATTR);
        return loggedUsers.remove(userLogin);
    }
}
