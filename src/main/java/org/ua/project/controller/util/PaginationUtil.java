package org.ua.project.controller.util;

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
}
