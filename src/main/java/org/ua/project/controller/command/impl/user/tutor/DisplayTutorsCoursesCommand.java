package org.ua.project.controller.command.impl.user.tutor;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
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
import java.util.Optional;

public class DisplayTutorsCoursesCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String url = ControllerConstants.FORWARD_TO_PERSONAL_CABINET;

        HttpSession session = req.getSession();
        User tutor = (User) session.getAttribute(ControllerConstants.USER_ATTR);
        CourseService courseService = new CourseService();
        Optional<String> courseStatusOpt = Optional.ofNullable(req.getParameter(Parameter.DISPLAY_TUTORS_COURSES.getValue()));
        CourseFilterOption.CourseStatus courseStatus;
        courseStatus = CourseFilterOption.CourseStatus.valueOf(courseStatusOpt.orElse("ongoing").toUpperCase());
        CourseFilterOption courseFilterOption = new CourseFilterOption.Builder()
                .setTutor(tutor)
                .setCourseStatus(courseStatus)
                .build();
        List<Course> tutorsCourses = courseService.getFilteredCourses(CourseSortParameter.BY_NAME_ASC, courseFilterOption);
        req.setAttribute("tutorsCourses", tutorsCourses);
        return url;
    }
}
