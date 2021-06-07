package org.ua.project.controller.command.impl;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LocalizationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String locale = req.getParameter(Parameter.LOCALE.getValue());
        req.getSession().setAttribute("locale", locale);

        Optional<String> lastRequest = Optional.ofNullable((String) req.getSession().getAttribute("lastRequest"));
        return ControllerConstants.REDIRECT_PREFIX + lastRequest.orElse("/main_page");
    }
}
