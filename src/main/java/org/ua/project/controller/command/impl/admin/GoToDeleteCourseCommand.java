package org.ua.project.controller.command.impl.admin;

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
        int courseId;
         try {
            courseId = Integer.parseInt(req.getParameter(Parameter.COURSE_ID.getValue()));
         } catch (NumberFormatException e) {
             logger.error(e);
             req.setAttribute(ControllerConstants.ERROR_ATR, "invalid_request_parameter");
             return ControllerConstants.FORWARD_TO_ERROR_PAGE;
         }
        String courseName = req.getParameter(Parameter.COURSE_NAME.getValue());
        Course course = new Course.Builder()
                .setName(courseName)
                .setId(courseId)
                .build();
        req.setAttribute("deletedCourse", course);
        String url = ControllerConstants.FORWARD_TO_DELETE_COURSE_PAGE + "?" + Parameter.COURSE_ID + "=" + courseId;
        return  PaginationUtil.appendPageFromRequest(url, req);
    }
}
