package org.ua.project.controller.util.authorization;

import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

public final class AuthorizationUtility {
   private AuthorizationUtility(){}

    public static void assignRole(HttpServletRequest req, String login, User.Role role) {
       req.getSession().setAttribute(ControllerConstants.USER_LOGIN_ATTR, login);
       req.getSession().setAttribute(ControllerConstants.USER_ROLE_ATTR, role);
    }

    public static boolean signInUser(HttpServletRequest req, String userLogin) {
        Set<String> users = (Set<String>) req.getSession().getServletContext().getAttribute(ControllerConstants.LOGGED_USERS_ATTR);
        if (!users.add(userLogin)) {
            return false;
        }
        return true;
    }

    public static void signOutUser(HttpServletRequest req, String userLogin) {
       HttpSession session = req.getSession();
       session.setAttribute(ControllerConstants.USER_LOGIN_ATTR, null);
       session.setAttribute(ControllerConstants.USER_ROLE_ATTR, User.Role.GUEST);
       Set<String> loggedUsers = (Set<String>) session.getServletContext().getAttribute(ControllerConstants.LOGGED_USERS_ATTR);
       loggedUsers.remove(userLogin);
   }
}
