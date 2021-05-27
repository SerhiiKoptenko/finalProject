package org.ua.project.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.command.impl.UserRegistrationCommand;
import org.ua.project.controller.command.impl.UserSignInCommand;
import org.ua.project.controller.command.impl.UserSignOutCommand;
import org.ua.project.controller.command.impl.gotocommands.*;
import org.ua.project.controller.constants.ControllerConstants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@WebServlet("/")
public class ControllerServlet extends HttpServlet {
    private static final Map<String, Command> commands = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(ControllerServlet.class);

    public void init(ServletConfig servletConfig){
       servletConfig.getServletContext()
                .setAttribute(ControllerConstants.LOGGED_USERS_ATTR, new HashSet<String>());

        commands.put("/registration_page?command=register", new UserRegistrationCommand());
        commands.put("/signIn_page?command=signIn", new UserSignInCommand());
        commands.put("/signOut", new UserSignOutCommand());

        commands.put("/registration_page", new GoToRegistrationPageCommand());
        commands.put("/sign_in_page", new GoToSignInPageCommand());
        commands.put("/admin/manage_courses", new GoToManageCoursesPageCommand());
        commands.put("/main_page", new GoToMainPageCommand());
        commands.put("/admin/admin_basis", new GoToAdminBasisCommand());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        logger.trace("received path " + path);
        String commandName = req.getParameter("command");
        if (commandName != null) {
            path += "?command=" + commandName;
        }
        Command command = commands.get(path);
        if (command != null) {
            String page = command.execute(req, resp);
            if (page.startsWith(ControllerConstants.REDIRECT_PREFIX)) {
                logger.trace("Redirecting to: " + page);
                resp.sendRedirect(page.replaceFirst(ControllerConstants.REDIRECT_PREFIX, ""));
            } else {
                logger.trace("Forwarding to: " + page);
                req.getRequestDispatcher(page).forward(req, resp);
            }
        } else {
            logger.error("unknown command: " + path);
        }
    }
}
