package org.ua.project.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.PaginationUtil;
import org.ua.project.model.entity.*;
import org.ua.project.model.entity.filter.CourseFilterOption;
import org.ua.project.model.entity.filter.CourseSortParameter;
import org.ua.project.model.service.CourseService;
import org.ua.project.model.service.ThemeService;
import org.ua.project.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * Command which fetches courses page based on filter and sorting criteria, and forwards to main page.
 */
public class GoToMainPageCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = new UserService();
        ThemeService themeService = new ThemeService();
        CourseService courseService = new CourseService();

        List<Theme> themeList = themeService.findAllThemes();
        List<User> tutorsList = userService.getUsersByRole(User.Role.TUTOR);
        req.setAttribute("themes", themeList);
        req.setAttribute("tutors", tutorsList);


        String sortBy = Optional.ofNullable(req.getParameter(Parameter.COURSE_SORT_OPTION.getValue())).orElse("courseNameAsc");
        CourseSortParameter sortParameter = CourseSortParameter.getFromRequestParameter(sortBy);

        Optional<String> tutorFilter = Optional.ofNullable(req.getParameter(Parameter.COURSE_TUTOR_ID.getValue()));
        Optional<String> themeFilter = Optional.ofNullable(req.getParameter(Parameter.COURSE_THEME_ID.getValue()));
        Optional<User> userFilter = Optional.ofNullable((User) req.getSession().getAttribute(ControllerConstants.USER_ATTR));

        User tutor = null;
        Theme theme = null;
        String tutorStr = tutorFilter.orElse("");
        String themeStr = themeFilter.orElse("");
        if (!tutorStr.isEmpty()) {
            try {
                int id = Integer.parseInt(tutorStr);
                tutor = new User.Builder()
                        .setId(id)
                        .build();
            } catch (NumberFormatException e) {
                logger.error(e);
                req.setAttribute(ControllerConstants.ERROR_ATR, "invalid_request_parameter");
                return ControllerConstants.FORWARD_TO_ERROR_PAGE;
            }
        }
        if (!themeStr.isEmpty()) {
            try {
                int id = Integer.parseInt(themeStr);
                theme = new Theme.Builder()
                        .setId(id)
                        .build();
            } catch (NumberFormatException e) {
                logger.error(e);
                req.setAttribute(ControllerConstants.ERROR_ATR, "invalid_request_parameter");
                return ControllerConstants.FORWARD_TO_ERROR_PAGE;
            }
        }

        CourseFilterOption filterOption = new CourseFilterOption(tutor, theme, userFilter.orElse(null), CourseFilterOption.CourseStatus.NOT_STARTED);

        int pageCount = PaginationUtil.getPagesCount(courseService.getAvailableCoursesCount(filterOption));
        int currentPage = PaginationUtil.getCurrentPage(req, pageCount);
        List<Course> coursesPage = courseService.getFilteredCoursesPage(currentPage, ControllerConstants.ITEMS_PER_PAGE, sortParameter, filterOption);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("coursesPage", coursesPage);
        return ControllerConstants.FORWARD_TO_MAIN_PAGE + "?sortOption=" + sortBy + "&page=" + currentPage;
    }

}
