package org.ua.project.model.dao.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ua.project.model.entity.*;
import org.ua.project.model.exception.DBException;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JDBCCourseDaoTest {
    public static final String SELECT_COURSE_BY_ID = "SELECT c.id course_id, c.name course_name, t.id theme_id, start_date, end_date, description, tutor_id " +
            "FROM courses c " +
            "INNER JOIN themes t ON c.theme_id = t.id "
            + "WHERE c.id = ?";

    private static Course courseA;
    private static Course courseB;
    private static User tutor;

    @Before
    public void setUp() throws SQLException {
        JdbcTestUtil.populateTables();

        tutor = new User.Builder()
                .setId(1)
                .setFirstName("Tutor")
                .setLastName("A")
                .setLogin("tutorA")
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

        courseB = new Course.Builder()
                .setId(2)
                .setName("Course B")
                .setTheme(new Theme(2, "Theme B"))
                .setStartDate(LocalDate.now().plus(Period.ofDays(30)))
                .setEndDate(LocalDate.now().plus(Period.ofDays(35)))
                .setDescription("Course B description")
                .setTutor(tutor)
                .build();
    }

    @Test
    public void testCreateCourse() throws SQLException, DBException {
        try (Connection connection = TestConnectionProvider.getConnection()) {
            JDBCCourseDao jdbcCourseDao = new JDBCCourseDao(connection);
            Theme testTheme = new Theme.Builder()
                    .setId(1)
                    .build();
            User testTutor = new User.Builder()
                    .setId(1)
                    .build();
            Course testCourse = new Course.Builder()
                    .setId(4)
                    .setName("testCourseName")
                    .setTheme(testTheme)
                    .setTutor(testTutor)
                    .setStartDate(LocalDate.parse("2021-06-20"))
                    .setEndDate(LocalDate.parse("2021-08-20"))
                    .setDescription("testDescription")
                    .build();
            jdbcCourseDao.createCourse(testCourse);
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COURSE_BY_ID);
            preparedStatement.setInt(1, 4);
            ResultSet resultSet = preparedStatement.executeQuery();
            Course course = extractCourse(resultSet);
            assertEquals(testCourse, course);
        }
    }

    private Course extractCourse(ResultSet resultSet) throws SQLException {
        resultSet.next();
        int courseId = resultSet.getInt("course_id");
        String courseName = resultSet.getString("course_name");
        int themeId = resultSet.getInt("theme_id");
        LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
        LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
        String description = resultSet.getString("description");
        int tutorId = resultSet.getInt("tutor_id");
        User tutor = null;
        if (tutorId != 0) {
            tutor = new User.Builder()
                    .setId(tutorId)
                    .build();
        }
        return new Course.Builder()
                .setId(courseId)
                .setName(courseName)
                .setTheme(new Theme(themeId, null))
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setDescription(description)
                .setTutor(tutor)
                .build();
    }

    @Test
    public void testFindCourseById() throws SQLException, DBException {
        try (Connection connection = TestConnectionProvider.getConnection()) {
            JDBCCourseDao jdbcCourseDao = new JDBCCourseDao(connection);
            assertEquals(courseA, jdbcCourseDao.findCourseById(1));
        }
    }

    @Test
    public void testUpdateCourse() throws SQLException, DBException {
        try (Connection connection = TestConnectionProvider.getConnection()) {
            JDBCCourseDao jdbcCourseDao = new JDBCCourseDao(connection);
            Course expected = new Course.Builder()
                    .setId(1)
                    .setName("Course A updated")
                    .setTutor(null)
                    .setTheme(new Theme(2, null))
                    .setStartDate(LocalDate.now())
                    .setEndDate(LocalDate.now().plus(Period.ofDays(5)))
                    .setDescription("Updated description A")
                    .build();
            jdbcCourseDao.updateCourse(expected);
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COURSE_BY_ID);
            preparedStatement.setInt(1, expected.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            Course updated = extractCourse(resultSet);
            assertEquals(expected, updated);
        }
    }

    @Test
    public void testDeleteCourse() throws SQLException, DBException {
        try (Connection connection = TestConnectionProvider.getConnection()) {
            JDBCCourseDao jdbcCourseDao = new JDBCCourseDao(connection);
            jdbcCourseDao.deleteCourse(3);
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COURSE_BY_ID);
            preparedStatement.setInt(1, 3);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertFalse(resultSet.next());
        }
    }

    @Test
    public void testGetFilteredCourses() throws SQLException, DBException {
        try (Connection connection = TestConnectionProvider.getConnection()) {
            JDBCCourseDao jdbcCourseDao = new JDBCCourseDao(connection);
            CourseSortParameter sortParameter = CourseSortParameter.BY_NAME_DESC;
            User tutor = new User.Builder()
                    .setId(1)
                    .build();
            CourseFilterOption filterOption = new CourseFilterOption.Builder()
                    .setTutor(tutor)
                    .build();
            List<Course> courseList = jdbcCourseDao.getFilteredCourses(sortParameter, filterOption);
            User expectedTutor = new User.Builder()
                    .setId(1)
                    .setFirstName("Tutor")
                    .setLastName("A")
                    .setLogin("tutorA")
                    .build();
            Course expectedCourse1 = new Course.Builder()
                    .setId(2)
                    .setName("Course B")
                    .setTheme(new Theme(2, "Theme B"))
                    .setStartDate(LocalDate.now().plus(Period.ofDays(30)))
                    .setEndDate(LocalDate.now().plus(Period.ofDays(35)))
                    .setDescription("Course B description")
                    .setTutor(expectedTutor)
                    .build();
            Course expectedCourse2 = new Course.Builder()
                    .setId(1)
                    .setName("Course A")
                    .setTheme(new Theme(1, "Theme A"))
                    .setStartDate(LocalDate.now().minus(Period.ofDays(30)))
                    .setEndDate(LocalDate.now().minus(Period.ofDays(5)))
                    .setDescription("Course A description")
                    .setTutor(expectedTutor)
                    .build();
            List<Course> expected = new ArrayList<>(Arrays.asList(expectedCourse1, expectedCourse2));
            assertEquals(expected, courseList);
        }
    }

    @Test
    public void testGetFilteredCoursesPage() throws SQLException, DBException {
        try (Connection connection = TestConnectionProvider.getConnection()) {
            JDBCCourseDao jdbcCourseDao = new JDBCCourseDao(connection);
            CourseSortParameter sortParameter = CourseSortParameter.BY_NAME_DESC;
            User tutor = new User.Builder()
                    .setId(1)
                    .build();
            CourseFilterOption filterOption = new CourseFilterOption.Builder()
                    .setTutor(tutor)
                    .build();
            List<Course> courseList = jdbcCourseDao.getFilteredCourses(0, 2, sortParameter, filterOption);
            List<Course> expected = new ArrayList<>(Arrays.asList(courseB, courseA));
            assertEquals(expected, courseList);
        }
    }

    @Test
    public void testGetFilteredCourseCount() throws SQLException, DBException {
        try (Connection connection = TestConnectionProvider.getConnection()) {
            JDBCCourseDao jdbcCourseDao = new JDBCCourseDao(connection);
            CourseFilterOption filterOption = new CourseFilterOption.Builder()
                    .setTutor(tutor)
                    .build();
            assertEquals(2, jdbcCourseDao.getFilteredCoursesCount(filterOption));
        }
    }

    @After
    public void tearDown() throws SQLException {
        JdbcTestUtil.truncateAllTables();
    }
}
