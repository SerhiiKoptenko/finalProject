package org.ua.project.model.dao.impl;

public class TestSqlStatements {
    private TestSqlStatements() {
    }


    public static final String CREATE_USER_STATEMENT = "INSERT INTO users (first_name, last_name, login, password, role) " +
            "VALUES (?, ?, ?, ?, ?)";

    public static final String CREATE_THEME_STATEMENT = "INSERT INTO themes (name) VALUES (?)";

    public static final String CREATE_COURSE_STATEMENT = "INSERT INTO courses (name, start_date, end_date, theme_id, tutor_id, description) " +
            "VALUES(?, ?, ?, ?, ?, ?)";
}
