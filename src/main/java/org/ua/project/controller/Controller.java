package org.ua.project.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.command.impl.UserRegistrationCommand;
import org.ua.project.controller.command.impl.gotocommands.goToRegistrationPageCommand;
import org.ua.project.controller.constants.ControllerConstants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

@WebServlet("/")
public class Controller extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();
    private Logger logger = LogManager.getLogger(Controller.class);
    private static final String COMMAND_PARAMETER = "command";

    public void init(ServletConfig servletConfig){
       /* servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());*/

        commands.put("/registration_page?command=register", new UserRegistrationCommand());
        commands.put("/registration_page", new goToRegistrationPageCommand());
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
            page = appendQueryString(req, page);
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

    public String appendQueryString(HttpServletRequest req, String path) {
        String result = path;
        String query = req.getQueryString();
        if (query != null) {
            result += "?" + query;
        }
        return result;
    }
}
