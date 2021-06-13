package org.ua.project.controller.command.impl.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.PaginationUtil;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;
import org.ua.project.model.service.ThemeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command which attempts to delete existing theme.
 */
public class RemoveThemeCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RemoveThemeCommand.class);

    private static final String ERROR_CANT_BE_DELETED = "cantBeDeleted";
    private static final String SUCCESS =  "success";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String url = ControllerConstants.REDIRECT_TO_MANAGE_COURSES_PAGE + "?themeRemoved=";

        int themeId = Integer.parseInt(req.getParameter(Parameter.COURSE_THEME_ID.getValue()));
        ThemeService themeService = new ThemeService();
        try {
            themeService.deleteTheme(themeId);
            logger.info("Attempt to remove theme with id {}", themeId);
        } catch (EntityNotFoundException e) {
            logger.error(e);
            req.setAttribute(ControllerConstants.ERROR_ATR, "no_specified_theme");
            return ControllerConstants.FORWARD_TO_ERROR_PAGE;
        } catch (IllegalDeletionException e) {
            url += ERROR_CANT_BE_DELETED;
            return PaginationUtil.appendPageFromRequest(url, req);
        }
        logger.info("Theme removed successfully");
        url += SUCCESS;
        return PaginationUtil.appendPageFromRequest(url, req);
    }
}
