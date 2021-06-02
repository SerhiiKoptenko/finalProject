package org.ua.project.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.service.CourseService;
import org.ua.project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
;

public class EnrollCommand implements Command {
    private static Logger logger = LogManager.getLogger(EnrollCommand.class);
    private static final String UNEXPECTED_ERROR = "unexpectedError";
    private static final String SUCCESS = "success";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String url = ControllerConstants.REDIRECT_PREFIX + "enroll" + "?enrollResult=";
        HttpSession session = req.getSession();
        User.Role userRole = (User.Role) session.getAttribute(ControllerConstants.USER_ROLE_ATTR);
        if (User.Role.GUEST.equals(userRole)) {
            return ControllerConstants.REDIRECT_PREFIX + "/sign_in_page?signInError=needToLogin";
        }
        String userLogin = (String) session.getAttribute(ControllerConstants.USER_LOGIN_ATTR);
        Optional<String> courseIdOpt = Optional.ofNullable(req.getParameter(Parameter.COURSE_ID.getValue()));
        if (!courseIdOpt.isPresent()) {
            return url + UNEXPECTED_ERROR;
        }

        Course course;
        try {
            int courseId = Integer.parseInt(courseIdOpt.get());
            course = new CourseService().findCourseById(courseId);
        } catch (NumberFormatException | EntityNotFoundException e) {
            return url + UNEXPECTED_ERROR;
        }
        UserService userService = new UserService();
        logger.debug("user {} attempts to enroll in course with id {}", userLogin, course.getId());
        userService.enrollUser(userLogin, course);

        return url + SUCCESS + "&courseName=" + course.getName();
    }
}
