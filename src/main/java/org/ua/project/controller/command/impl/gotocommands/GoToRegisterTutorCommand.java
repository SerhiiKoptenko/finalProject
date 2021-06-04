package org.ua.project.controller.command.impl.gotocommands;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToRegisterTutorCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        return ControllerConstants.FORWARD_TO_REGISTER_TUTOR_PAGE;
    }
}
