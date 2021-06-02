package org.ua.project.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.exception.UnparseableDateException;
import org.ua.project.controller.util.ControllerUtil;
import org.ua.project.controller.util.PaginationUtil;
import org.ua.project.controller.util.validation.ValidationResult;
import org.ua.project.controller.util.validation.Validator;
import org.ua.project.model.entity.Course;
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


        Optional<String> courseIdOpt = Optional.ofNullable(req.getParameter("courseId"));
        if (!courseIdOpt.isPresent()) {
            logger.error("no couser id specified");
            //TODO: goto error page
            return null;
        }

        int courseId;
        try {
            courseId = Integer.parseInt(courseIdOpt.get());
        } catch (NumberFormatException e) {
            logger.error("can't parse course id");
            return null;
        }

        try {
            if (courseId < 1) {
                logger.error("course id < 1");
                //TODO: goto error page
            }
            course = ControllerUtil.extractCourseFromRequest(req);
            course.setId(courseId);
        } catch (UnparseableDateException e) {
            return ControllerConstants.REDIRECT_TO_EDIT_COURSE_PAGE + UPDATE_RESULT + ERROR_INVALID_DATA
                    + "&invalid_" + Parameter.COURSE_START_DATE_OR_END_DATE.getValue();
        } catch (NumberFormatException e) {
            logger.error("can't parse theme id");
            //TODO: goto error page
            return null;
        }

        Validator validator = Validator.getInstance();
        ValidationResult validationResult = validator.validateCourse(course);

        if (!validationResult.isSuccessful()) {
            String url = ControllerConstants.REDIRECT_TO_EDIT_COURSE_PAGE;
            url += UPDATE_RESULT + ERROR_INVALID_DATA + validationResult.getInvalidParametersString();
            url += COURSE_ID + courseId;
            return url;
        }

        CourseService courseService = new CourseService();
        courseService.updateCourse(course);

        String url = ControllerConstants.REDIRECT_TO_MANAGE_COURSES_PAGE + UPDATE_RESULT + SUCCESS;
        return PaginationUtil.appendPageFromRequest(url, req);
    }
}
