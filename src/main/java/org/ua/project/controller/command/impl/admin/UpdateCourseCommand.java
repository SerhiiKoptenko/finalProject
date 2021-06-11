package org.ua.project.controller.command.impl.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.exception.InvalidRequestParameterException;
import org.ua.project.controller.exception.UnparseableDateException;
import org.ua.project.controller.util.ControllerUtil;
import org.ua.project.controller.util.PaginationUtil;
import org.ua.project.controller.util.validation.ValidationResult;
import org.ua.project.controller.util.validation.Validator;
import org.ua.project.model.entity.Course;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class UpdateCourseCommand implements Command {
    Logger logger = LogManager.getLogger(UpdateCourseCommand.class);

    private static final String UPDATE_RESULT = "?updateResult=";
    private static final String ERROR_INVALID_DATA = "errorInvalidData?";
    private static final String SUCCESS = "success";
    private static final String COURSE_ID = "&" + Parameter.COURSE_ID.getValue() + "=";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Course course;

        try {
            course = ControllerUtil.extractCourseFromRequest(req);
        } catch (UnparseableDateException e) {
            return ControllerConstants.REDIRECT_TO_EDIT_COURSE_PAGE + UPDATE_RESULT + ERROR_INVALID_DATA
                    + "&invalid_" + Parameter.COURSE_START_DATE_OR_END_DATE.getValue();
        } catch (InvalidRequestParameterException e) {
            logger.error(e);
            req.setAttribute(ControllerConstants.ERROR_ATR, "invalid_request_parameter");
            return ControllerConstants.FORWARD_TO_ERROR_PAGE;
        }

        Validator validator = Validator.getInstance();
        ValidationResult validationResult = validator.validateCourse(course);

        if (!validationResult.isSuccessful()) {
            String url = ControllerConstants.REDIRECT_TO_EDIT_COURSE_PAGE;
            url += UPDATE_RESULT + ERROR_INVALID_DATA + validationResult.getInvalidParametersString();
            url += COURSE_ID + course.getId();
            return url;
        }

        CourseService courseService = new CourseService();
       try {
           courseService.updateCourse(course);
       } catch (EntityNotFoundException e) {
           logger.error(e);
           req.setAttribute(ControllerConstants.ERROR_ATR, "no_specified_course");
           return ControllerConstants.FORWARD_TO_ERROR_PAGE;
       }

        String url = ControllerConstants.REDIRECT_TO_MANAGE_COURSES_PAGE + UPDATE_RESULT + SUCCESS;
        return PaginationUtil.appendPageFromRequest(url, req);
    }
}
