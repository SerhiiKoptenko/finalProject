package org.ua.project.controller.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;


public class LocalizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        Optional<String> localeOpt = Optional.ofNullable(req.getParameter("locale"));
        HttpSession session = req.getSession();
        localeOpt.ifPresent(locale -> session.setAttribute("locale", locale));
        chain.doFilter(request, response);
     }
}
