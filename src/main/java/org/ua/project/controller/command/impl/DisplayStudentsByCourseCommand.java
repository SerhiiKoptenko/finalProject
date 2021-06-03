package org.ua.project.controller.command.impl;

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

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Optional<String> courseIdOpt = Optional.ofNullable(req.getParameter(Parameter.COURSE_ID.getValue()));
        int courseId = Integer.parseInt(courseIdOpt.orElse("0"));

        Course course = new Course.Builder()
                .setId(courseId)
                .build();

        StudentCourseService studentCourseService = new StudentCourseService();
        List<StudentCourse> studentsByCourse = studentCourseService.getStudentsByCourse(course);
        req.setAttribute("studentsByCourse", studentsByCourse);
        return ControllerConstants.FORWARD_TO_PERSONAL_CABINET;
    }
}
