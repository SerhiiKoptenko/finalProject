package org.ua.project.controller.command.impl.gotocommands;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GoToEditCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToEditCourseCommand.class);

    ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id;
        try {
            id = Integer.parseInt(req.getParameter(Parameter.COURSE_ID.getValue()));
        } catch (NumberFormatException e) {
            logger.error("can't parse course id");
            //TODO:: goto error page
            return null;
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
            //TODO: go to error page
            return null;
        }

        course.setId(id);
        themes.remove(course.getTheme());
        Optional<User> tutorOpt = Optional.ofNullable(course.getTutor());
        tutorOpt.ifPresent(tutor -> tutors.removeIf(user -> user.getLogin().equals(tutor.getLogin())));
        req.setAttribute("editedCourse", course);
        req.setAttribute("tutors", tutors);
        req.setAttribute("themes", themes);
        return ControllerConstants.FORWARD_TO_EDIT_COURSE_PAGE;
    }
}
