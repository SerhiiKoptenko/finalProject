package org.ua.project.controller.command.impl.user.tutor;

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
import java.io.IOException;

public class UpdateStudentsMarkCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateStudentsMarkCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int mark;
        int studId;
        int courseId;

        try {
            mark = Integer.parseInt(req.getParameter(Parameter.MARK.getValue()));
            studId = Integer.parseInt(req.getParameter(Parameter.STUDENT_ID.getValue()));
            courseId = Integer.parseInt(req.getParameter(Parameter.COURSE_ID.getValue()));
        } catch (NumberFormatException e) {
            logger.error(e);
            req.setAttribute(ControllerConstants.ERROR_ATR, "invalid_request_parameter");
            return ControllerConstants.FORWARD_TO_ERROR_PAGE;
        }


        User student = new User.Builder()
                .setId(studId)
                .build();
        Course course = new Course.Builder()
                .setId(courseId)
                .build();
        StudentCourse studentCourse = new StudentCourse.Builder()
                .setCourse(course)
                .setStudent(student)
                .setMark(mark)
                .build();
        StudentCourseService studentCourseService = new StudentCourseService();
        studentCourseService.updateStudentsMark(studentCourse);
        return ControllerConstants.REDIRECT_TO_JOURNAL + "?command=displayTutorsJournal&courseId=" + courseId;
    }
}
