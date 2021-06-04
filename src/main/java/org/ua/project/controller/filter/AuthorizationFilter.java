package org.ua.project.controller.filter;

import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AuthorizationFilter extends HttpFilter {
    private static final Map<User.Role, List<String>> accessMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        List<String> guestForbiddenUrls = new ArrayList<>();
        accessMap.put(User.Role.GUEST, guestForbiddenUrls);

        List<String> userForbiddenUrls = new ArrayList<>();
        userForbiddenUrls.add("/sign_in_page");
        userForbiddenUrls.add("/registration_page");
        accessMap.put(User.Role.STUDENT, userForbiddenUrls);

        List<String> adminForbiddenUrls = new ArrayList<>();
        adminForbiddenUrls.add("/sign_in_page");
        adminForbiddenUrls.add("/registration_page");
        accessMap.put(User.Role.ADMIN, adminForbiddenUrls);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        String url = request.getRequestURI();
        User.Role role = (User.Role) request.getSession().getAttribute(ControllerConstants.USER_ROLE_ATTR);
        if (accessMap.get(role).contains(url)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            chain.doFilter(req, res);
        }
    }
}
