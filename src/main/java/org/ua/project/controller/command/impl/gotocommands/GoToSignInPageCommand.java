package org.ua.project.controller.command.impl.gotocommands;

import org.ua.project.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToSignInPageCommand implements Command {
    private static final String LOGIN_PAGE = "/WEB-INF/jsp/signIn.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        return LOGIN_PAGE;
    }
}
