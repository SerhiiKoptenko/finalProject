package org.ua.project.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.ControllerUtil;
import org.ua.project.controller.util.PaginationUtil;
import org.ua.project.model.entity.Course;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;
import org.ua.project.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class DeleteCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteCourseCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Optional<String> courseIdOpt = Optional.ofNullable(req.getParameter(Parameter.COURSE_ID.getValue()));
        if (!courseIdOpt.isPresent()) {
            //TODO: go to error page
            logger.error("no course id");
            return null;
        }
        int courseId;
        try {
            courseId = Integer.parseInt(courseIdOpt.get());
        } catch (NumberFormatException e) {
            logger.error(e);
            return null;
        }

        CourseService courseService = new CourseService();
        try {
            courseService.deleteCourseById(courseId);
        } catch (IllegalDeletionException e) {
            logger.error(e);
        } catch (EntityNotFoundException e) {
            logger.error(e);
        }

        String url = ControllerConstants.REDIRECT_TO_MANAGE_COURSES_PAGE + "?deleteResult=success";
        return PaginationUtil.appendPageFromRequest(url, req);
    }
}
