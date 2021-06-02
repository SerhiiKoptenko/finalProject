package org.ua.project.controller.command.impl.gotocommands;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.*;
import org.ua.project.service.CourseService;
import org.ua.project.service.ThemeService;
import org.ua.project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GoToManageCoursesPageCommand implements Command {

    private static final String MANAGE_COURSES_PAGE = "/WEB-INF/jsp/admin/manage_courses.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = new UserService();
        ThemeService themeService = new ThemeService();
        CourseService courseService = new CourseService();

        List<Theme> themeList = themeService.findAllThemes();
        List<User> tutorsList = userService.getUsersByRole(User.Role.TUTOR);
        req.setAttribute("themes", themeList);
        req.setAttribute("tutors", tutorsList);

        Optional<String> currentPageOpt = Optional.ofNullable(req.getParameter("page"));
        int currentPage = Integer.parseInt(currentPageOpt.orElse("1"));

        int courseCount = courseService.getAvailiableCoursesCount(new CourseFilterOption());
        int pageCount = courseCount / ControllerConstants.ITEMS_PER_PAGE;

        if (courseCount % pageCount != 0) {
            pageCount++;
        }

        if (currentPage > pageCount || currentPage < 1) {
            currentPage = 1;
        }

        List<Course> coursesPage = courseService.getAvailableCoursePage(currentPage, ControllerConstants.ITEMS_PER_PAGE, CourseSortParameter.BY_NAME_ASC, new CourseFilterOption());

        req.setAttribute("currentPage", currentPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("coursesPage", coursesPage);
        return MANAGE_COURSES_PAGE;
    }
}
