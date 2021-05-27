package org.ua.project.controller.command.impl.gotocommands;

import org.ua.project.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToMainPageCommand implements Command {
    private static final String MAIN_PAGE = "/WEB-INF/jsp/main.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        return MAIN_PAGE;
    }
}
