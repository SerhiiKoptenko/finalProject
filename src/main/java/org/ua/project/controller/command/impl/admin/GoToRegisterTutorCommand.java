package org.ua.project.controller.command.impl.admin;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command which forwards to tutor registration page.
 */
public class GoToRegisterTutorCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return ControllerConstants.FORWARD_TO_REGISTER_TUTOR_PAGE;
    }
}
