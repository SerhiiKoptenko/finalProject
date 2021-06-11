package org.ua.project.controller.command.impl.user.student;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalInsertionException;
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
    private static final Logger logger = LogManager.getLogger(EnrollCommand.class);
    private static final String UNEXPECTED_ERROR = "unexpectedError";
    private static final String SUCCESS = "success";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String url = ControllerConstants.REDIRECT_TO_ENROLL_PAGE + "?enrollResult=";
        HttpSession session = req.getSession();
        int courseId;
        try {
            courseId = Integer.parseInt(req.getParameter(Parameter.COURSE_ID.getValue()));
        } catch (NumberFormatException e) {
            logger.error(e);
            req.setAttribute(ControllerConstants.ERROR_ATR, "invalid_request_parameter");
            return ControllerConstants.FORWARD_TO_ERROR_PAGE;
        }

        User student = (User) session.getAttribute(ControllerConstants.USER_ATTR);
        UserService userService = new UserService();
        logger.debug("user {} attempts to enroll in course with id {}",  student.getLogin(), courseId);
        try {
            userService.enrollStudent(student.getId(), courseId);
        } catch (EntityNotFoundException e) {
            logger.error(e);
            req.setAttribute(ControllerConstants.ERROR_ATR, "no_specified_course");
        } catch (IllegalInsertionException e) {
            logger.error(e);
            req.setAttribute(ControllerConstants.ERROR_ATR, "cant_enroll_in_started_course");
        }

        return url + SUCCESS;
    }
}
