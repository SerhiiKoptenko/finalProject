package org.ua.project.controller.command.impl;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.CourseFilterOption;
import org.ua.project.model.entity.CourseSortParameter;
import org.ua.project.model.entity.User;
import org.ua.project.model.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class DisplayTutorsCoursesCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String url = ControllerConstants.FORWARD_TO_PERSONAL_CABINET;

        HttpSession session = req.getSession();
        User tutor = (User) session.getAttribute(ControllerConstants.USER_ATTR);
        CourseService courseService = new CourseService();
        CourseFilterOption courseFilterOption = new CourseFilterOption.Builder()
                .setTutor(tutor)
                .build();
        List<Course> tutorsCourses = courseService.getFilteredCourses(CourseSortParameter.BY_NAME_ASC, courseFilterOption);
        req.setAttribute("tutorsCourses", tutorsCourses);
        return url;
    }
}
