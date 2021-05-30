package org.ua.project.controller.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.exception.UnparseableDateException;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class ControllerUtil {
    private static final Logger logger = LogManager.getLogger(ControllerUtil.class);

    private ControllerUtil(){}

    public static Course extractCourseFromRequest(HttpServletRequest req) throws UnparseableDateException {
        String courseName = req.getParameter(Parameter.COURSE_NAME.getValue());
        int themeId = Integer.parseInt(req.getParameter(Parameter.COURSE_THEME_ID.getValue()));
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(req.getParameter(Parameter.COURSE_START_DATE.getValue()));
            endDate = LocalDate.parse(req.getParameter(Parameter.COURSE_END_DATE.getValue()));
        } catch (DateTimeParseException e) {
            logger.error(e);
            throw new UnparseableDateException();
        }

        String description = req.getParameter(Parameter.COURSE_DESCRIPTION.getValue());
        String tutorIdParam = req.getParameter(Parameter.COURSE_TUTOR_ID.getValue());

        User tutor = null;
        if (!tutorIdParam.isEmpty()) {
            int tutorId = Integer.parseInt(tutorIdParam);
            tutor = new User.Builder()
                    .setId(tutorId)
                    .build();
        }

        Theme theme = new Theme.Builder()
                .setId(themeId)
                .build();

        return new Course.Builder()
                .setName(courseName)
                .setTheme(theme)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setDescription(description)
                .setTutor(tutor)
                .build();
    }
}
