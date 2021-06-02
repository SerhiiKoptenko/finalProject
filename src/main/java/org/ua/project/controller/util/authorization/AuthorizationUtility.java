package org.ua.project.controller.util.authorization;

import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

public final class AuthorizationUtility {
   private AuthorizationUtility(){}

    public static void saveUserToSession(HttpServletRequest req, User user) {
       req.getSession().setAttribute(ControllerConstants.USER_ATTR, user);
       req.getSession().setAttribute(ControllerConstants.USER_ROLE_ATTR, user.getRole());
    }

    public static boolean signInUser(HttpServletRequest req, String userLogin) {
        Set<String> users = (Set<String>) req.getSession().getServletContext().getAttribute(ControllerConstants.LOGGED_USERS_ATTR);
        return users.add(userLogin);
    }

    public static void signOutUser(HttpServletRequest req, String userLogin) {
       HttpSession session = req.getSession();
       session.setAttribute(ControllerConstants.USER_ATTR, null);
       session.setAttribute(ControllerConstants.USER_ROLE_ATTR, User.Role.GUEST);
       Set<String> loggedUsers = (Set<String>) session.getServletContext().getAttribute(ControllerConstants.LOGGED_USERS_ATTR);
       loggedUsers.remove(userLogin);
   }
}
