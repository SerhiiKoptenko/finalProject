package org.ua.project.controller.command.impl;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocalizationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String locale = req.getParameter(Parameter.LOCALE.getValue());
        req.getSession().setAttribute("locale", locale);
        return ControllerConstants.REDIRECT_TO_MAIN_PAGE;
    }
}
