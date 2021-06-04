package org.ua.project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.StudentCourseDao;
import org.ua.project.model.dao.mapper.CourseMapper;
import org.ua.project.model.dao.mapper.UserMapper;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.CourseFilterOption;
import org.ua.project.model.entity.StudentCourse;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.util.SqlStatementLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCStudentCourseDao extends JDBCAbstractDao implements StudentCourseDao {
    private static final Logger logger = LogManager.getLogger(JDBCStudentCourseDao.class);

    private static final String FIND_COURSES_BY_STUDENT;

    private static final String FIND_ONGOING;
    public static final String FIND_COMPLETED;
    public static final String FIND_NOT_STARTED;
    public static final String FIND_STUDENTS_BY_COURSE;
    public static final String UPDATE_STUDENTS_MARK;
    public static final String REMOVE_STUDENT_FROM_COURSE;

    static {
        SqlStatementLoader loader = SqlStatementLoader.getInstance();
        FIND_COURSES_BY_STUDENT = loader.getSqlStatement("findCoursesByStudent");
        FIND_ONGOING = loader.getSqlStatement("findOngoing");
        FIND_COMPLETED = loader.getSqlStatement("findCompleted");
        FIND_NOT_STARTED = loader.getSqlStatement("findNotStarted");
        FIND_STUDENTS_BY_COURSE = loader.getSqlStatement("findStudentsByCourse");
        UPDATE_STUDENTS_MARK = loader.getSqlStatement("updateStudentsMark");
        REMOVE_STUDENT_FROM_COURSE = loader.getSqlStatement("removeStudentFromCourse");
    }

    protected JDBCStudentCourseDao(Connection connection) {
        super(connection);
    }

    @Override
    public void create(StudentCourse entity) throws DBException {

    }

    @Override
    public StudentCourse findById(int id) throws DBException {
        return null;
    }

    @Override
    public List<StudentCourse> findAll() throws DBException {
        return null;
    }

    @Override
    public void update(StudentCourse entity) throws DBException {

    }

    @Override
    public void delete(int id) throws DBException {

    }

    public List<StudentCourse> findCoursesByStudent(User student, CourseFilterOption courseFilterOption) throws DBException {
        CourseFilterOption.CourseStatus courseStatus = courseFilterOption.getCourseStatus();
        String filterByCourseStatus;
        if (CourseFilterOption.CourseStatus.COMPLETED.equals(courseStatus)) {
            filterByCourseStatus = FIND_COMPLETED;
        } else if (CourseFilterOption.CourseStatus.NOT_STARTED.equals(courseStatus)) {
            filterByCourseStatus = FIND_NOT_STARTED;
        } else {
            filterByCourseStatus = FIND_ONGOING;
        }

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
    public boolean updateStudentsMark(StudentCourse studentCourse) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENTS_MARK)) {
            preparedStatement.setInt(1, studentCourse.getMark());
            preparedStatement.setInt(2, studentCourse.getCourse().getId());
            preparedStatement.setInt(3, studentCourse.getStudent().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public boolean removeStudentFromCourse(StudentCourse studentCourse) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_STUDENT_FROM_COURSE)) {
            preparedStatement.setInt(1, studentCourse.getStudent().getId());
            preparedStatement.setInt(2, studentCourse.getCourse().getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public void close() throws DBException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
