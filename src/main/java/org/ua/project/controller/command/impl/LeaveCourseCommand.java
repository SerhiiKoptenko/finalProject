package org.ua.project.controller.command.impl;

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


        User student = new User.Builder()
                .setId(studId)
                .build();
        Course course = new Course.Builder()
                .setId(courseId)
                .build();
        StudentCourse studentCourse = new StudentCourse.Builder()
                .setStudent(student)
                .setCourse(course)
                .build();
        StudentCourseService studentCourseService = new StudentCourseService();
        studentCourseService.removeStudentFromCourse(studentCourse);
        return ControllerConstants.REDIRECT_TO_PERSONAL_CABINET + "?leftCourseName=" + courseName;
    }
}
