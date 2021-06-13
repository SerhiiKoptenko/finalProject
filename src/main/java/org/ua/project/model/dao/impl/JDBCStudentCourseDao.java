package org.ua.project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.StudentCourseDao;
import org.ua.project.model.dao.mapper.CourseMapper;
import org.ua.project.model.dao.mapper.UserMapper;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.filter.CourseFilterOption;
import org.ua.project.model.entity.StudentCourse;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalInsertionException;
import org.ua.project.model.util.SqlStatementLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC StudentCourseDao implementation.
 */
public class JDBCStudentCourseDao extends JDBCAbstractDao implements StudentCourseDao {
    private static final Logger logger = LogManager.getLogger(JDBCStudentCourseDao.class);

    private static final String FIND_COURSES_BY_STUDENT;

    private static final String FIND_ONGOING;
    public static final String FIND_COMPLETED;
    public static final String FIND_NOT_STARTED;
    public static final String FIND_STUDENTS_BY_COURSE;
    public static final String UPDATE_STUDENTS_MARK;
    public static final String REMOVE_STUDENT_FROM_COURSE;
    public static final String ENROLL_USER;


    static {
        SqlStatementLoader loader = SqlStatementLoader.getInstance();
        FIND_COURSES_BY_STUDENT = loader.getSqlStatement("findCoursesByStudent");
        FIND_ONGOING = loader.getSqlStatement("findOngoing");
        FIND_COMPLETED = loader.getSqlStatement("findCompleted");
        FIND_NOT_STARTED = loader.getSqlStatement("findNotStarted");
        FIND_STUDENTS_BY_COURSE = loader.getSqlStatement("findStudentsByCourse");
        UPDATE_STUDENTS_MARK = loader.getSqlStatement("updateStudentsMark");
        REMOVE_STUDENT_FROM_COURSE = loader.getSqlStatement("removeStudentFromCourse");
        ENROLL_USER = loader.getSqlStatement("enrollUser");
    }

    protected JDBCStudentCourseDao(Connection connection) {
        super(connection);
    }

    public List<StudentCourse> findCoursesByStudent(User student, CourseFilterOption courseFilterOption) throws DBException {
        CourseFilterOption.CourseStatus courseStatus = courseFilterOption.getCourseStatus();
        String filterByCourseStatus;
        filterByCourseStatus = getFilterByStatement(courseStatus);

        String sql = String.format(FIND_COURSES_BY_STUDENT, filterByCourseStatus);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, student.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            CourseMapper courseMapper = new CourseMapper();

            List<StudentCourse> studentCourses = new ArrayList<>();
            while (resultSet.next()) {
                Course course = courseMapper.extract(resultSet);
                int mark = resultSet.getInt("mark");
                studentCourses.add(new StudentCourse.Builder()
                        .setMark(mark)
                        .setCourse(course)
                        .setStudent(student)
                        .build());
            }
            return studentCourses;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private String getFilterByStatement(CourseFilterOption.CourseStatus courseStatus) {
        if (CourseFilterOption.CourseStatus.COMPLETED.equals(courseStatus)) {
            return FIND_COMPLETED;
        }
        if (CourseFilterOption.CourseStatus.NOT_STARTED.equals(courseStatus)) {
            return FIND_NOT_STARTED;
        }
        if (CourseFilterOption.CourseStatus.ONGOING.equals(courseStatus)){
            return FIND_ONGOING;
        }
        return "0 = 0";
    }

    @Override
    public List<StudentCourse> findStudentsByCourse(Course course) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_STUDENTS_BY_COURSE)) {
            List<StudentCourse> studentCourseList = new ArrayList<>();
            preparedStatement.setInt(1, course.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            UserMapper userMapper = new UserMapper();
            while (resultSet.next()) {
                User student = userMapper.extractStudentData(resultSet);
                int mark = resultSet.getInt("mark");
                studentCourseList.add(new StudentCourse.Builder()
                        .setStudent(student)
                        .setCourse(course)
                        .setMark(mark)
                        .build());
            }
            return studentCourseList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public void enrollStudent(int studId, int courseId) throws DBException, EntityNotFoundException, IllegalInsertionException {
        try (PreparedStatement enrollStudentStatement = connection.prepareStatement(ENROLL_USER)) {
            connection.setAutoCommit(false);
            JDBCCourseDao courseDao = new JDBCCourseDao(connection);
            Course course = courseDao.findCourseById(courseId);
            if (course.getEndDate().compareTo(LocalDate.now()) < 0) {
                throw new IllegalInsertionException();
            }
            enrollStudentStatement.setInt(1, studId);
            enrollStudentStatement.setInt(2, courseId);
            enrollStudentStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
           try {
               connection.rollback();
           } catch (SQLException excep) {
               logger.error(excep);
           }
            logger.error(e);
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }

    @Override
    public void updateStudentsMark(StudentCourse studentCourse) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENTS_MARK)) {
            preparedStatement.setInt(1, studentCourse.getMark());
            preparedStatement.setInt(2, studentCourse.getCourse().getId());
            preparedStatement.setInt(3, studentCourse.getStudent().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public void removeStudentFromCourse(int studId, int courseId) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_STUDENT_FROM_COURSE)) {
            preparedStatement.setInt(1, studId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }
}
