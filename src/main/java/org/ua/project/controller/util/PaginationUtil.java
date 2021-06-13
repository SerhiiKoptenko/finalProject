package org.ua.project.controller.util;

import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;


import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Utility class for pagination.
 */
public final class PaginationUtil {
    private PaginationUtil(){}

    /**
     * Extracts page number from request and append to given url.
     * @param url - url for appending page number.
     * @param req - request from which page number should be extracted.
     * @return url with appended page number.
     */
    public static String appendPageFromRequest(String url, HttpServletRequest req) {
        String result = url;
        Optional<String> pageOpt = Optional.ofNullable(req.getParameter(Parameter.PAGE.getValue()));
        result += "&"+ Parameter.PAGE.getValue() + "=" + pageOpt.orElse("1");
        return result;
    }

    /**
     * Returns total page count.
     * @param entitiesCount - count of all entities to be displayed.
     * @return total page count.
     */
    public static int getPagesCount(int entitiesCount) {
        int pageCount = entitiesCount / ControllerConstants.ITEMS_PER_PAGE;
        if (entitiesCount % ControllerConstants.ITEMS_PER_PAGE != 0) {
            pageCount++;
        }
        return pageCount;
    }

    /**
     * Extracts page to be displayed from request.
     * @return page as an int, or 1 if page number in request is invalid.
     */
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
