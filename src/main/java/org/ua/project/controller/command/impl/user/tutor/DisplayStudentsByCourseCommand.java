package org.ua.project.controller.command.impl.user.tutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.StudentCourse;
import org.ua.project.model.service.StudentCourseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DisplayStudentsByCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DisplayStudentsByCourseCommand.class);


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

        Course course = new Course.Builder()
                .setId(courseId)
                .build();

        StudentCourseService studentCourseService = new StudentCourseService();
        List<StudentCourse> studentsByCourse = studentCourseService.getStudentsByCourse(course);
        req.setAttribute("studentsByCourse", studentsByCourse);
        return ControllerConstants.FORWARD_TO_JOURNAL;
    }
}
