package org.ua.project.controller.command.impl.gotocommands;

import org.ua.project.controller.command.Command;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.entity.User;
import org.ua.project.service.CourseService;
import org.ua.project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GoToManageCoursesPageCommand implements Command {

    private static final String MANAGE_COURSES_PAGE = "/WEB-INF/jsp/admin/manage_courses.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        CourseService courseService = new CourseService();
        UserService userService = new UserService();
        List<Course> coursesList = courseService.getAllCourses();
        Set<Theme> themesSet = new LinkedHashSet<>();
        coursesList.forEach(course -> themesSet.add(course.getTheme()));
        List<User> tutorsList = userService.getUsersByRole(User.Role.TUTOR);
        req.setAttribute("themes", themesSet);
        req.setAttribute("tutors", tutorsList);
        req.setAttribute("courses", coursesList);
        return MANAGE_COURSES_PAGE;
    }
}
