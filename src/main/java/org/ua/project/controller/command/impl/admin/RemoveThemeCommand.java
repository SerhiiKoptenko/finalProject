package org.ua.project.controller.command.impl.admin;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.PaginationUtil;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;
import org.ua.project.model.service.ThemeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveThemeCommand implements Command {
    private static final String ERROR_NO_SUCH_THEME = "noSuchTheme";
    private static final String ERROR_CANT_BE_DELETED = "cantBeDeleted";
    private static final String PREV_THEME_NAME = "&prevRemoveThemeName=";
    private static final String SUCCESS =  "success";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String url = ControllerConstants.REDIRECT_TO_MANAGE_COURSES_PAGE + "?themeRemoved=";

        int themeId = Integer.parseInt(req.getParameter(Parameter.COURSE_THEME_ID.getValue()));
        ThemeService themeService = new ThemeService();
        try {
            themeService.deleteTheme(themeId);
        } catch (EntityNotFoundException e) {
            url += ERROR_NO_SUCH_THEME;
            return PaginationUtil.appendPageFromRequest(url, req);
        } catch (IllegalDeletionException e) {
            url += ERROR_CANT_BE_DELETED;
            return PaginationUtil.appendPageFromRequest(url, req);
        }
        url += SUCCESS;
        return PaginationUtil.appendPageFromRequest(url, req);
    }
}
