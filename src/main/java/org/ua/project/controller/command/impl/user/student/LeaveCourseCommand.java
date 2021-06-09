package org.ua.project.controller.command.impl.user.student;

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

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String courseName = req.getParameter(Parameter.COURSE_NAME.getValue());
        int studId = Integer.parseInt(req.getParameter(Parameter.STUDENT_ID.getValue()));
        int courseId = Integer.parseInt(req.getParameter(Parameter.COURSE_ID.getValue()));

        StudentCourseService studentCourseService = new StudentCourseService();
        studentCourseService.removeStudentFromCourse(studId, courseId);
        return ControllerConstants.REDIRECT_TO_PERSONAL_CABINET + "?leftCourseName=" + courseName;
    }
}
