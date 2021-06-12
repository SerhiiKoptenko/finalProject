package org.ua.project.controller.command.impl.admin;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.User;
import org.ua.project.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToManageStudentsCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = new UserService();
        req.setAttribute("users", userService.getUsersByRole(User.Role.STUDENT));
        return ControllerConstants.FORWARD_TO_MANAGE_STUDENTS_PAGE;
    }
}