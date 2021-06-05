package org.ua.project.controller.command.impl.admin;

import org.ua.project.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAdminBasisCommand implements Command {
    private static final String ADMIN_BASIS = "/WEB-INF/jsp/admin/admin_basis.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        return ADMIN_BASIS;
    }
}
