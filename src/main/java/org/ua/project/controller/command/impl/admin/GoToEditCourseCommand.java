package org.ua.project.controller.command.impl.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.service.CourseService;
import org.ua.project.model.service.ThemeService;
import org.ua.project.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * Command which forwards to edit course page and sets necessary request attributes.
 */
public class GoToEditCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToEditCourseCommand.class);


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int id;
        try {
            id = Integer.parseInt(req.getParameter(Parameter.COURSE_ID.getValue()));
        } catch (NumberFormatException e) {
            logger.error(e);
            req.setAttribute(ControllerConstants.ERROR_ATR, "invalid_request_parameter");
            return ControllerConstants.FORWARD_TO_ERROR_PAGE;
        }

        CourseService courseService = new CourseService();
        ThemeService themeService = new ThemeService();
        UserService userService = new UserService();

        List<User> tutors = userService.getUsersByRole(User.Role.TUTOR);
        List<Theme> themes = themeService.findAllThemes();
        Course course;
        try {
            course = courseService.findCourseById(id);
        } catch (EntityNotFoundException e) {
            logger.error(e);
            req.setAttribute(ControllerConstants.ERROR_ATR, "no_specified_course");
            return ControllerConstants.FORWARD_TO_ERROR_PAGE;
        }

        course.setId(id);
        themes.remove(course.getTheme());
        Optional<User> tutorOpt = Optional.ofNullable(course.getTutor());
        tutorOpt.ifPresent(tutor -> tutors.removeIf(t -> t.getLogin().equals(tutor.getLogin())));
        req.setAttribute("editedCourse", course);
        req.setAttribute("tutors", tutors);
        req.setAttribute("themes", themes);
        return ControllerConstants.FORWARD_TO_EDIT_COURSE_PAGE;
    }
}
