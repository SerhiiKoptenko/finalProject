package org.ua.project.controller.command.impl;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.entity.User;
import org.ua.project.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserBlockedStatusCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        boolean newStatus = Boolean.parseBoolean(req.getParameter(Parameter.BLOCK.getValue()));
        int userId = Integer.parseInt(req.getParameter(Parameter.USER_ID.getValue()));
        User user = new User.Builder()
                .setId(userId)
                .setBlocked(newStatus)
                .build();
        UserService userService = new UserService();
        userService.updateUserBlockedStatus(user);
        return ControllerConstants.REDIRECT_TO_MANAGE_USERS_PAGE;
    }
}
