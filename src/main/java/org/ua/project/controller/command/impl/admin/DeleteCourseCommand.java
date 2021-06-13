package org.ua.project.controller.command.impl.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;
import org.ua.project.model.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command to delete existing course.
 */
public class DeleteCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteCourseCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String url = ControllerConstants.REDIRECT_TO_MANAGE_COURSES_PAGE + "?deleteResult=";
        int courseId;
        try {
            courseId = Integer.parseInt(req.getParameter(Parameter.COURSE_ID.getValue()));
        } catch (NumberFormatException e) {
            logger.error(e);
            req.setAttribute(ControllerConstants.ERROR_ATR, "invalid_request_parameter");
            return ControllerConstants.FORWARD_TO_ERROR_PAGE;
        }

        CourseService courseService = new CourseService();
        try {
            courseService.deleteCourseById(courseId);
        } catch (IllegalDeletionException e) {
            return url + "cantDelete";
        } catch (EntityNotFoundException e) {
            logger.error(e);
            req.setAttribute(ControllerConstants.ERROR_ATR, "no_specified_course");
            return ControllerConstants.FORWARD_TO_ERROR_PAGE;
        }

        return url + "success";
    }
}
