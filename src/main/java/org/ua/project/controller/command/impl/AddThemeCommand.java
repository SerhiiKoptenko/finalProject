package org.ua.project.controller.command.impl;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.util.PaginationUtil;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.service.ThemeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddThemeCommand implements Command {

    private static final String ERROR_EMPTY_NAME = "empty";
    private static final String ERROR_ALREADY_EXITS = "themeAlreadyExists";
    private static final String PREV_THEME_NAME = "&prevAddThemeName=";
    private static final String SUCCESS =  "success";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String url = ControllerConstants.REDIRECT_TO_MANAGE_COURSES_PAGE + "?themeAdded=";

        String themeName = req.getParameter("themeName");
        if (themeName.isEmpty()) {
            return url + ERROR_EMPTY_NAME;
        }
        Theme theme = new Theme.Builder()
                .setName(themeName)
                .build();
        ThemeService themeService = new ThemeService();

        try {
            themeService.createTheme(theme);
        } catch (EntityAlreadyExistsException e) {
            url += ERROR_ALREADY_EXITS;
            url += PREV_THEME_NAME + theme.getName();
            return url;
        }

        url += url + SUCCESS;
        return PaginationUtil.appendPageFromRequest(url, req);
    }
}
