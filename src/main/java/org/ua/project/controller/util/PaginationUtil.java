package org.ua.project.controller.util;

import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;


import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public final class PaginationUtil {
    private PaginationUtil(){}

    public static String appendPageFromRequest(String url, HttpServletRequest req) {
        String result = url;
        Optional<String> pageOpt = Optional.ofNullable(req.getParameter(Parameter.PAGE.getValue()));
        result += "&"+ Parameter.PAGE.getValue() + "=" + pageOpt.orElse("1");
        return result;
    }

    public static int getPagesCount(int entitiesCount) {
        int pageCount = entitiesCount / ControllerConstants.ITEMS_PER_PAGE;
        if (entitiesCount % ControllerConstants.ITEMS_PER_PAGE != 0) {
            pageCount++;
        }
        return pageCount;
    }

    public static int getCurrentPage(HttpServletRequest req, int pageCount) {
        Optional<String> currentPageOpt = Optional.ofNullable(req.getParameter("page"));
        int currentPage;
        try {
            currentPage = Integer.parseInt(currentPageOpt.orElse("1"));
        } catch (NumberFormatException e) {
            currentPage = 1;
        }
        if (currentPage > pageCount || currentPage < 1) {
            currentPage = 1;
        }
        return currentPage;
    }
}
