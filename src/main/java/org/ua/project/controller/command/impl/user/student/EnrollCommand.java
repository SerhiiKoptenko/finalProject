package org.ua.project.controller.command.impl.user.student;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.service.CourseService;
import org.ua.project.model.service.UserService;

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
        String url = ControllerConstants.REDIRECT_TO_ENROLL_PAGE + "?enrollResult=";
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ControllerConstants.USER_ATTR);
        if (User.Role.GUEST.equals(user.getRole())) {
            return ControllerConstants.REDIRECT_PREFIX + "/sign_in_page?signInError=needToLogin";
        }


        Optional<User> studentOpt = Optional.ofNullable((User) session.getAttribute(ControllerConstants.USER_ATTR));
        Optional<String> courseIdOpt = Optional.ofNullable(req.getParameter(Parameter.COURSE_ID.getValue()));
        if (!courseIdOpt.isPresent() || !studentOpt.isPresent()) {
            return url + UNEXPECTED_ERROR;
        }

        int courseId;
        try {
            courseId = Integer.parseInt(courseIdOpt.get());
        } catch (NumberFormatException e) {
            return url + UNEXPECTED_ERROR;
        }


        User student = studentOpt.get();
        UserService userService = new UserService();
        logger.debug("user {} attempts to enroll in course with id {}",  student.getLogin(), courseId);
        try {
            userService.enrollStudent(studentOpt.get().getId(), courseId);
        } catch (Exception e) {
            //TODO: handle
        }

        return url + SUCCESS;
    }
}
