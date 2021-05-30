package org.ua.project.controller.constants;

public enum Parameter {
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    LOGIN("login"),
    PASSWORD("password"),
    PAGE("page"),

    COURSE_ID("courseId"),
    COURSE_NAME("courseName"),
    COURSE_THEME("themeName"),
    COURSE_THEME_ID("themeId"),
    COURSE_START_DATE("startDate"),
    COURSE_END_DATE("endDate"),
    COURSE_DESCRIPTION("description"),
    COURSE_TUTOR_ID("tutorId"),
    COURSE_START_DATE_OR_END_DATE("startDateOrEndDate");
    private String value;

    Parameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
