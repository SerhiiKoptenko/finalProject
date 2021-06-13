package org.ua.project.controller.command.impl.user.student;

import org.ua.project.controller.command.Command;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.entity.filter.CourseFilterOption;
import org.ua.project.model.entity.StudentCourse;
import org.ua.project.model.entity.User;
import org.ua.project.model.service.StudentCourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * Commands which displays courses in which students has enrolled.
 */
public class DisplayCoursesByStudentCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String url = ControllerConstants.FORWARD_TO_PERSONAL_CABINET;
        User user = (User) req.getSession().getAttribute(ControllerConstants.USER_ATTR);
        Optional<String> courseStatusOpt = Optional.ofNullable(req.getParameter(Parameter.DISPLAY_STUDENTS_COURSES.getValue()));
        CourseFilterOption.CourseStatus courseStatus;
        courseStatus = CourseFilterOption.CourseStatus.valueOf(courseStatusOpt.orElse("not_started").toUpperCase());
        StudentCourseService studentCourseService = new StudentCourseService();
        CourseFilterOption filterOption = new CourseFilterOption.Builder()
                .setCourseStatus(courseStatus)
                .build();
        List<StudentCourse> studentCourses = studentCourseService.getCoursesByStudent(user, filterOption);
        req.setAttribute("studentsCourses", studentCourses);
        return url;
    }
}
