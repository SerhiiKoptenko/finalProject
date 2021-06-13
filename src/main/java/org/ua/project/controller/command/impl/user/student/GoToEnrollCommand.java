package org.ua.project.controller.command.impl.user.student;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command which forwards to enroll page.
 */
public class GoToEnrollCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return ControllerConstants.FORWARD_TO_ENROLL_PAGE;
    }
}
