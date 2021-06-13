package org.ua.project.controller.util.authorization;

import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * Utility for user authorization and authentication.
 */
public class AuthorizationUtility {
    //suppress default constructor
    private AuthorizationUtility(){
        throw new AssertionError();
    }

    /**
     * Saves user entity to session associated with given request.
     * @param req - request in which session user should be saved.
     * @param user - user entity to be saved.
     */
    public static void saveUserToSession(HttpServletRequest req, User user) {
       req.getSession().setAttribute(ControllerConstants.USER_ATTR, user);
    }

    /**
     * Saves user login to application context.
     * @param req - request for obtaining application context.
     * @param userLogin - login to be saved.
     * @return true if context didn't already contain users login, false otherwise.
     */
    public static boolean signInUser(HttpServletRequest req, String userLogin) {
        Set<String> users = (Set<String>) req.getSession().getServletContext().getAttribute(ControllerConstants.LOGGED_USERS_ATTR);
        return users.add(userLogin);
    }

    /**
     * Removes user login from application context and sets user role as guest.
     * @param req - request for obtaining user session and application context.
     * @param userLogin - login of user to be singed out.
     * @return true if user hasn't been already signed out, false otherwise.
     */
    public static boolean signOutUser(HttpServletRequest req, String userLogin) {
       User user = new User.Builder()
               .setRole(User.Role.GUEST)
               .build();
       HttpSession session = req.getSession();
       session.setAttribute(ControllerConstants.USER_ATTR, user);
       Set<String> loggedUsers = (Set<String>) session.getServletContext().getAttribute(ControllerConstants.LOGGED_USERS_ATTR);
       return loggedUsers.remove(userLogin);
   }
}
