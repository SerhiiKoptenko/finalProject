package org.ua.project.controller.command.impl.admin;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.User;
import org.ua.project.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command which extracts all students and forwards to manage students page.
 */
public class GoToManageStudentsCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = new UserService();
        req.setAttribute("users", userService.getUsersByRole(User.Role.STUDENT));
        return ControllerConstants.FORWARD_TO_MANAGE_STUDENTS_PAGE;
    }
}
