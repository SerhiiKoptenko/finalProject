package org.ua.project.controller.command.impl.gotocommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.PaginationUtil;
import org.ua.project.model.entity.Course;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class GoToDeleteCourseCommand implements Command {
    private static Logger logger = LogManager.getLogger(GoToDeleteCourseCommand.class);
    
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Optional<String> courseIdOpt = Optional.ofNullable(req.getParameter(Parameter.COURSE_ID.getValue()));
        if (!courseIdOpt.isPresent()) {
            logger.error("no course id");
            //TODO: go to error page
            return null;
        }

        int courseId;
        try {
            courseId = Integer.parseInt(courseIdOpt.get());
        } catch (NumberFormatException e) {
            logger.error(e);
            //TODO: go to error page
            return null;
        }

        CourseService courseService = new CourseService();
        Course course;
        try {
            course = courseService.findCourseById(courseId);
        } catch (EntityNotFoundException e) {
            logger.error(e);
            //TODO: go to error page
            return null;
        }

        req.setAttribute("deletedCourse", course);
        String url = ControllerConstants.FORWARD_TO_DELETE_COURSE_PAGE + "?" + Parameter.COURSE_ID + "=" + courseId;
        return  PaginationUtil.appendPageFromRequest(url, req);
    }
}
