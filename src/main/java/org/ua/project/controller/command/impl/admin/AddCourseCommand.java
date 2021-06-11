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

public class AddCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddCourseCommand.class);

    private static final String ERROR_INVALID_DATA = "errorInvalidData";
    private static final String ADD_RESULT = "?addResult=";
    private static final String SUCCESS = "success";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String url = ControllerConstants.REDIRECT_TO_MANAGE_COURSES_PAGE;
        Course course;
        try {
            course = ControllerUtil.extractCourseFromRequest(req);
        } catch (UnparseableDateException e) {
            return url + ADD_RESULT + ERROR_INVALID_DATA + "&_invalid" + Parameter.COURSE_START_DATE_OR_END_DATE.getValue();
        } catch (InvalidRequestParameterException e) {
            logger.error(e);
            req.setAttribute(ControllerConstants.ERROR_ATR, "invalid_request_parameter");
            return ControllerConstants.FORWARD_TO_ERROR_PAGE;
        }

        Validator validator = Validator.getInstance();
        ValidationResult validationResult = validator.validateCourse(course);

        if (!validationResult.isSuccessful()) {
            return url + ADD_RESULT + ERROR_INVALID_DATA + validationResult.getInvalidParametersString();
        }

        CourseService service = new CourseService();
       try {
           service.createCourse(course);
       } catch (EntityNotFoundException e) {
           req.setAttribute(ControllerConstants.ERROR_ATR, "no_theme_or_tutor");
            return ControllerConstants.FORWARD_TO_ERROR_PAGE;
       }
       url += ADD_RESULT + SUCCESS;
       return PaginationUtil.appendPageFromRequest(url, req);
    }
}
