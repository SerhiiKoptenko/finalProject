package org.ua.project.controller.command.impl;

import org.ua.project.controller.command.Command;
import org.ua.project.model.entity.Course;
import org.ua.project.model.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowAllCoursesCommand implements Command {

    private static final String MANAGE_COURSES_PAGE = "/WEB-INF/jsp/manageCourses.jsp";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        CourseService courseService = new CourseService();
        List<Course> coursesList = courseService.findAllCourses();
        req.setAttribute("coursesList", coursesList);
        return MANAGE_COURSES_PAGE;
    }
}
