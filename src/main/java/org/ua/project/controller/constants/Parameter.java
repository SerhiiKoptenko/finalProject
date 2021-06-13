package org.ua.project.controller.constants;

/**
 * Enum class which holds HTTP request parameters names.
 */
public enum Parameter {
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    LOGIN("login"),
    PASSWORD("password"),
    PAGE("page"),
    USER_ID("userId"),

    COURSE_ID("courseId"),
    COURSE_NAME("courseName"),
    COURSE_THEME("themeName"),
    COURSE_THEME_ID("themeId"),
    COURSE_START_DATE("startDate"),
    COURSE_END_DATE("endDate"),
    COURSE_DESCRIPTION("description"),
    COURSE_TUTOR_ID("tutorId"),
    COURSE_START_DATE_OR_END_DATE("startDateOrEndDate"),
    COURSE_SORT_OPTION("sortOption"),

    COURSE_SORT_DURATION_ASC("courseDurationAsc"),
    COURSE_SORT_DURATION_DESC("courseDurationDesc"),
    COURSE_SORT_STUDENTS_ASC("courseStudentsAsc"),
    COURSE_SORT_STUDENTS_DESC("courseStudentsDesc"),
    COURSE_SORT_NAME_DESC("courseNameDesc"),
    COURSE_SORT_NAME_ASC("courseNameAsc"),

    DISPLAY_STUDENTS_COURSES("displayedCourses"),
    DISPLAY_TUTORS_COURSES("displayTutorsCourses"),
    DISPLAY_COURSES("displayCourses"),

    MARK("mark"),
    STUDENT_ID("studId"),

    LOCALE("locale"),

    BLOCK("block");

    private final String value;


    Parameter(String value) {
        this.value = value;
    }

    /**
     * Returns string value of parameter.
     * @return string value of parameter
     */
    public String getValue() {
        return value;
    }
}
