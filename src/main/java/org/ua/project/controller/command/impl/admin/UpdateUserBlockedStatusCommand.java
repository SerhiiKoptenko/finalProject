package org.ua.project.controller.command.impl.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class UpdateUserBlockedStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateUserBlockedStatusCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        boolean blocked = Boolean.parseBoolean(req.getParameter(Parameter.BLOCK.getValue()));
        String userLogin = req.getParameter(Parameter.LOGIN.getValue());
        int userId = Integer.parseInt(req.getParameter(Parameter.USER_ID.getValue()));
        User user = new User.Builder()
                .setLogin(userLogin)
                .setId(userId)
                .setBlocked(blocked)
                .build();
        UserService userService = new UserService();
       try {
           userService.updateUserBlockedStatus(user);
       } catch (EntityNotFoundException e) {
           logger.error(e);
           req.setAttribute(ControllerConstants.ERROR_ATR, "no_specified_user");
           return ControllerConstants.FORWARD_TO_ERROR_PAGE;
       }
       if (blocked) {
           Set<String> loggedUsers = (Set<String>) req.getSession().getServletContext().getAttribute(ControllerConstants.LOGGED_USERS_ATTR);
           loggedUsers.remove(user.getLogin());
       }
        return ControllerConstants.REDIRECT_TO_MANAGE_USERS_PAGE;
    }
}
