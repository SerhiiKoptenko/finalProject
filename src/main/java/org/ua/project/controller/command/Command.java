package org.ua.project.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Encapsulates actions which will be executed by main controller.
 */
public interface Command {

    /**
     * Executes actions associated with this command and returns url for controller to redirect/forward to.
     *
     * @param req - HttpServletRequest which will be processed by command.
     * @param resp - HttpServletResponse which will be processed by command.
     * @return - url for controller to redirect/forward to.
     */
    String execute(HttpServletRequest req, HttpServletResponse resp);

}
