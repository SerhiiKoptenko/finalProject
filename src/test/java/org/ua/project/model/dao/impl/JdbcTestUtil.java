package org.ua.project.model.dao.impl;

import org.ua.project.model.entity.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTestUtil {
    private JdbcTestUtil(){}
    public static void populateTables() throws SQLException {
        try (Connection connection = TestConnectionProvider.getConnection()) {
           PreparedStatement preparedStatement =  connection.prepareStatement("INSERT INTO themes (name) VALUES ('Theme A'), ('Theme B'), ('Theme C');\n" +
                   "\n" +
                   "INSERT INTO users (first_name, last_name, login, password, role)\n" +
                   "VALUES ('Tutor', 'A', 'tutorA', 4444, 'TUTOR'),\n" +
                   "('Student', 'A', 'studentA', 4444, 'STUDENT'),\n" +
                   "('Student', 'B', 'studentB', 4444, 'STUDENT');\n" +
                   "\n" +
                   "INSERT INTO courses (name, start_date, end_date, theme_id, tutor_id, description)\n" +
                   "VALUES ('Course A', DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), 1, 1, 'Course A description'),\n" +
                   "('Course B', DATE_ADD(NOW(), INTERVAL 30 DAY), DATE_ADD(NOW(), INTERVAL 35 DAY), 2, 1, 'Course B description'),\n" +
                    "('Course C', DATE_ADD(NOW(), INTERVAL 5 DAY), DATE_ADD(NOW(), INTERVAL 10 DAY), 1, NULL, 'Course C description');\n" +
                   "INSERT INTO students_courses (stud_id, course_id, mark)\n" +
                   "VALUES (2, 1, 60), (3, 1, 90), (2, 2, NULL), (3, 2, NULL);");
            preparedStatement.executeUpdate();
        }
    }

    public static void truncateAllTables() throws SQLException {
        try (Connection connection = TestConnectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SET FOREIGN_KEY_CHECKS = 0;\n" +
                    "TRUNCATE themes;\n" +
                    "TRUNCATE users;\n" +
                    "TRUNCATE courses;\n" +
                    "TRUNCATE students_courses;\n" +
                    "SET FOREIGN_KEY_CHECKS  = 1;");
            preparedStatement.executeUpdate();
        }
    }
}
