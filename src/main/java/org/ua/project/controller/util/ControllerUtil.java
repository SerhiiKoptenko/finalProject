package org.ua.project.controller.util;

import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.exception.InvalidRequestParameterException;
import org.ua.project.controller.exception.UnparseableDateException;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Utility class for controller use.
 */
public final class ControllerUtil {

    //suppress default constructor
    private ControllerUtil(){
        throw new AssertionError();
    }

    /**
     * Attempts to extract course entity from request parameters.
     * @param req - HttpServletRequest from which course should be extracted.
     * @return extracted course entity.
     * @throws UnparseableDateException - if course start date or/and end date cannot be converted to LocalDate instance.
     * @throws InvalidRequestParameterException - if request contains invalid course parameter.
     */
    public static Course extractCourseFromRequest(HttpServletRequest req) throws UnparseableDateException, InvalidRequestParameterException {
        String courseName = req.getParameter(Parameter.COURSE_NAME.getValue());

        int courseId = 0;
        Optional<String> courseIdParameter = Optional.ofNullable(req.getParameter(Parameter.COURSE_ID.getValue()));

        if (courseIdParameter.isPresent()) {
            try {
                courseId = Integer.parseInt(courseIdParameter.get());
            } catch (NumberFormatException e) {
                throw new InvalidRequestParameterException(e);
            }
        }

        int themeId;
        try {
            themeId = Integer.parseInt(req.getParameter(Parameter.COURSE_THEME_ID.getValue()));
        } catch (NumberFormatException e) {
            throw new InvalidRequestParameterException();
        }
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(req.getParameter(Parameter.COURSE_START_DATE.getValue()));
            endDate = LocalDate.parse(req.getParameter(Parameter.COURSE_END_DATE.getValue()));
        } catch (DateTimeParseException e) {
            throw new UnparseableDateException();
        }

        String description = req.getParameter(Parameter.COURSE_DESCRIPTION.getValue());
        String tutorIdParam = req.getParameter(Parameter.COURSE_TUTOR_ID.getValue());

        User tutor = null;
        if (!tutorIdParam.isEmpty()) {
            int tutorId;
            try {
                tutorId = Integer.parseInt(tutorIdParam);
            } catch (NumberFormatException e) {
                throw new InvalidRequestParameterException(e);
            }
            tutor = new User.Builder()
                    .setId(tutorId)
                    .build();
        }

        Theme theme = new Theme.Builder()
                .setId(themeId)
                .build();

        return new Course.Builder()
                .setId(courseId)
                .setName(courseName)
                .setTheme(theme)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setDescription(description)
                .setTutor(tutor)
                .build();
    }
}
