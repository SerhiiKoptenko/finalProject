package org.ua.project.model.dao.impl;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ua.project.model.dao.StudentCourseDao;
import org.ua.project.model.entity.*;
import org.ua.project.model.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class JdbcStudentCourseDaoTest {
    private User tutor;
    private Course courseB;
    private Course courseA;

    private static final String FIND_STUDENT_COURSE_BY_IDS = "SELECT * FROM students_courses " +
            "WHERE stud_id = ? AND course_id = ?";

    @Before
    public void setUp() throws SQLException {
        JdbcTestUtil.populateTables();

        tutor = new User.Builder()
                .setId(1)
                .setFirstName("Tutor")
                .setLastName("A")
                .setLogin("tutorA")
                .build();
        courseB = new Course.Builder()
                .setId(2)
                .setName("Course B")
                .setTheme(new Theme(2, "Theme B"))
                .setStartDate(LocalDate.now().plus(Period.ofDays(30)))
                .setEndDate(LocalDate.now().plus(Period.ofDays(35)))
                .setDescription("Course B description")
                .setTutor(tutor)
                .build();
        courseA = new Course.Builder()
                .setId(1)
                .setName("Course A")
                .setTheme(new Theme(1, "Theme A"))
                .setStartDate(LocalDate.now().minus(Period.ofDays(30)))
                .setEndDate(LocalDate.now().minus(Period.ofDays(5)))
                .setTutor(tutor)
                .setDescription("Course A description")
                .build();
    }

    @Test
    public void testFindCoursesByStudent() throws SQLException, DBException {
        User student = new User.Builder()
                .setId(2)
                .setFirstName("Student")
                .setLastName("A")
                .setLogin("studentA")
                .setPassword("4444")
                .setRole(User.Role.STUDENT)
                .build();
        StudentCourse studentCourseB = new StudentCourse.Builder()
                .setStudent(student)
                .setCourse(courseB)
                .setMark(0)
                .build();
        List<StudentCourse> expected = new ArrayList<>(Arrays.asList(studentCourseB));
        try (Connection connection = TestConnectionProvider.getConnection()) {
            StudentCourseDao studentCourseDao = new JDBCStudentCourseDao(connection);
            CourseFilterOption filterOption = new CourseFilterOption.Builder()
                    .setCourseStatus(CourseFilterOption.CourseStatus.NOT_STARTED)
                    .build();
            List<StudentCourse> actual = studentCourseDao.findCoursesByStudent(student, filterOption);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testFindStudentsByCourse() throws SQLException, DBException {
        User studentA = new User.Builder()
                .setId(2)
                .setFirstName("Student")
                .setLastName("A")
                .build();
        User studentB = new User.Builder()
                .setId(3)
                .setFirstName("Student")
                .setLastName("B")
                .build();
        StudentCourse studentACourse = new StudentCourse.Builder()
                .setCourse(courseA)
                .setStudent(studentA)
                .setMark(60)
                .build();
        StudentCourse studentBCourse = new StudentCourse.Builder()
                .setCourse(courseA)
                .setStudent(studentB)
                .setMark(90)
                .build();
        List<StudentCourse> expectedList = new ArrayList<>(Arrays.asList(studentACourse, studentBCourse));
        try (Connection connection = TestConnectionProvider.getConnection()) {
            StudentCourseDao studentCourseDao = new JDBCStudentCourseDao(connection);
            List<StudentCourse> actualList = studentCourseDao.findStudentsByCourse(courseA);
            assertEquals(expectedList, actualList);
        }
    }

    @Test
    public void testEnrollStudent() throws SQLException, DBException {
        int studId = 2;
        int courseId = 3;
        try (Connection connection = TestConnectionProvider.getConnection()) {
            StudentCourseDao studentCourseDao = new JDBCStudentCourseDao(connection);
            studentCourseDao.enrollStudent(2, 3);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_STUDENT_COURSE_BY_IDS);
            preparedStatement.setInt(1, studId);
            preparedStatement.setInt(2, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertEquals(studId, resultSet.getInt("stud_id"));
            assertEquals(courseId, resultSet.getInt("course_id"));
            assertEquals(0, resultSet.getInt("mark"));
        }
    }

    @Test
    public void testRemoveStudentFromCourse() throws SQLException, DBException {
        int studId = 2;
        int courseId = 1;
        try (Connection connection = TestConnectionProvider.getConnection()) {
            StudentCourseDao studentCourseDao = new JDBCStudentCourseDao(connection);
            studentCourseDao.removeStudentFromCourse(2, 1);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_STUDENT_COURSE_BY_IDS);
            preparedStatement.setInt(1, studId);
            preparedStatement.setInt(2, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertFalse(resultSet.next());
        }
    }

    @Test
    public void testUpdateStudentMark() throws SQLException, DBException {
        User studentA = new User.Builder()
                .setId(2)
                .build();
        StudentCourse studentCourse = new StudentCourse.Builder()
                .setCourse(courseA)
                .setStudent(studentA)
                .setMark(30)
                .build();
        try (Connection connection = TestConnectionProvider.getConnection()) {
            StudentCourseDao studentCourseDao = new JDBCStudentCourseDao(connection);
            studentCourseDao.updateStudentsMark(studentCourse);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_STUDENT_COURSE_BY_IDS);
            preparedStatement.setInt(1, studentA.getId());
            preparedStatement.setInt(2, courseA.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertEquals(studentCourse.getMark(), resultSet.getInt("mark"));
        }
    }


    @After
    public void tearDown() throws SQLException {
        JdbcTestUtil.truncateAllTables();
    }
}
