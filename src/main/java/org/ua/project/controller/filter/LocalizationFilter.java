package org.ua.project.controller.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Filter for switching user locale.
 */
public class LocalizationFilter implements Filter {
    /**
     * Extracts locale if present and saves it to session.
     * @param request - the request to process.
     * @param response - the response associated with request.
     * @param chain - to pass request and response for further processing.
     * @throws IOException - if an exception has occurred that interferes with the filterChain's normal operation.
     * @throws ServletException - if an I/O related error has occurred during the processing.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        Optional<String> localeOpt = Optional.ofNullable(req.getParameter("locale"));
        HttpSession session = req.getSession();
        localeOpt.ifPresent(locale -> session.setAttribute("locale", locale));
        chain.doFilter(request, response);
     }
}
