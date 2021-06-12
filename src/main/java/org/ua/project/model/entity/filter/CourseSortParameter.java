package org.ua.project.model.entity.filter;

import org.ua.project.controller.constants.Parameter;

public enum CourseSortParameter {
    BY_NAME_ASC,
    BY_NAME_DESC,
    BY_DURATION_ASC,
    BY_DURATION_DESC,
    BY_STUDENTS_ASC,
    BY_STUDENTS_DESC;

    public static CourseSortParameter getFromRequestParameter(String value) {
        if (Parameter.COURSE_SORT_NAME_ASC.getValue().equals(value)) {
            return BY_NAME_ASC;
        }
        if (Parameter.COURSE_SORT_NAME_DESC.getValue().equals(value)) {
            return BY_NAME_DESC;
        }
        if (Parameter.COURSE_SORT_DURATION_DESC.getValue().equals(value)) {
            return BY_DURATION_DESC;
        }
        if (Parameter.COURSE_SORT_DURATION_ASC.getValue().equals(value)) {
            return BY_DURATION_ASC;
        }
        if (Parameter.COURSE_SORT_STUDENTS_ASC.getValue().equals(value)) {
            return BY_STUDENTS_ASC;
        }
        if (Parameter.COURSE_SORT_STUDENTS_DESC.getValue().equals(value)) {
            return BY_STUDENTS_DESC;
        }
        return BY_NAME_ASC;
    }
}
