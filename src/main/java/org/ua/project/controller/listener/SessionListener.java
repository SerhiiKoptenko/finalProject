package org.ua.project.controller.listener;

import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Set;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        User user = new User.Builder()
                .setRole(User.Role.GUEST)
                .build();
        se.getSession().setAttribute(ControllerConstants.USER_ATTR, user);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String userLogin = (String) se.getSession().getAttribute(ControllerConstants.USER_ATTR);
        Set<String> userLogins = (Set<String>) se.getSession().getServletContext().getAttribute(ControllerConstants.LOGGED_USERS_ATTR);
        userLogins.remove(userLogin);
    }
}
