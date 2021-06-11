package org.ua.project.controller.command.impl.user.student;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.StudentCourse;
import org.ua.project.model.entity.User;
import org.ua.project.model.service.StudentCourseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

public class LeaveCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LeaveCourseCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String courseName = req.getParameter(Parameter.COURSE_NAME.getValue());
        int studId;
        int courseId;
        try {
            studId = Integer.parseInt(req.getParameter(Parameter.STUDENT_ID.getValue()));
            courseId = Integer.parseInt(req.getParameter(Parameter.COURSE_ID.getValue()));
        } catch (NumberFormatException e) {
            logger.error(e);
            req.setAttribute(ControllerConstants.ERROR_ATR, "invalid_request_parameter");
            return ControllerConstants.FORWARD_TO_ERROR_PAGE;
        }

        StudentCourseService studentCourseService = new StudentCourseService();
        studentCourseService.removeStudentFromCourse(studId, courseId);
        return ControllerConstants.REDIRECT_TO_PERSONAL_CABINET + "?leftCourseName=" + courseName;
    }
}
