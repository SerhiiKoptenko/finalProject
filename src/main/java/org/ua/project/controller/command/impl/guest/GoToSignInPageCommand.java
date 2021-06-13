package org.ua.project.controller.command.impl.guest;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command which forwards to sign in page.
 */
public class GoToSignInPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return ControllerConstants.FORWARD_TO_SIGN_IN;
    }
}
