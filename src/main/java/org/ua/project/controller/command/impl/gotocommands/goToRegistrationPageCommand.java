package org.ua.project.controller.command.impl.gotocommands;

import org.ua.project.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class goToRegistrationPageCommand implements Command {

    private static final String REGISTRATION_PAGE = "/WEB-INF/jsp/registration.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        return REGISTRATION_PAGE;
    }
}
