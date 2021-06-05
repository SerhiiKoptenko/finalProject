package org.ua.project.controller.command.impl.user.tutor;

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

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int mark = Integer.parseInt(req.getParameter(Parameter.MARK.getValue()));
        int studId = Integer.parseInt(req.getParameter(Parameter.STUDENT_ID.getValue()));
        int courseId = Integer.parseInt(req.getParameter(Parameter.COURSE_ID.getValue()));
        if (mark < 1 || mark  > 100) {
            throw new IllegalArgumentException();
        }
        if (studId < 0) {
            throw new IllegalArgumentException();
        }
        if (courseId < 0) {
            throw new IllegalArgumentException();
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
