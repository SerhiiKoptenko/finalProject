package org.ua.project.controller.command.impl;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.CourseFilterOption;
import org.ua.project.model.entity.StudentCourse;
import org.ua.project.model.entity.User;
import org.ua.project.model.service.StudentCourseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DisplayStudentsCoursesCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String url = ControllerConstants.FORWARD_TO_PERSONAL_CABINET;
        User user = (User) req.getSession().getAttribute(ControllerConstants.USER_ATTR);
        StudentCourseService studentCourseService = new StudentCourseService();
        CourseFilterOption filterOption = new CourseFilterOption.Builder()
                .setCourseStatus(CourseFilterOption.CourseStatus.COMPLETED)
                .build();
        List<StudentCourse> studentCourses = studentCourseService.getCoursesByStudent(user, filterOption);
        req.setAttribute("studentsCourses", studentCourses);
        return url;
    }
}
