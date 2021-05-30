package org.ua.project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.CourseDao;
import org.ua.project.model.dao.impl.mapper.CourseMapper;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;
import org.ua.project.model.util.SqlStatementLoader;

import java.sql.*;
import java.util.List;

public class JDBCCourseDao extends JDBCAbstractDao implements CourseDao {
    private static Logger logger = LogManager.getLogger(JDBCCourseDao.class);

    private static final String FIND_ALL_COURSES;
    private static final String FIND_COURSE_BY_ID;
    private static final String CREATE_COURSE;
    private static final String CREATE_COURSE_WITH_TUTOR;
    private static final String COUNT_COURSES;
    private static final String GET_PAGE_SORTED_BY_THEME;
    private static final String UPDATE_COURSE;
    private static final String UPDATE_COURSE_WITH_TUTOR;
    private static final String DELETE_COURSE_BY_ID;
    private static final String FIND_COURSE_NAME_BY_ID;

    static {
        SqlStatementLoader loader = SqlStatementLoader.getInstance();
        FIND_ALL_COURSES = loader.getSqlStatement("findAllCourses");
        CREATE_COURSE = loader.getSqlStatement("createCourse");
        CREATE_COURSE_WITH_TUTOR = loader.getSqlStatement("createCourseWithTutor");
        COUNT_COURSES = loader.getSqlStatement("countCourses");
        GET_PAGE_SORTED_BY_THEME = loader.getSqlStatement("getPageSortedByThemeName");
        FIND_COURSE_BY_ID = loader.getSqlStatement("findCourseById");
        UPDATE_COURSE = loader.getSqlStatement("updateCourse");
        UPDATE_COURSE_WITH_TUTOR = loader.getSqlStatement("updateCourseWithTutor");
        DELETE_COURSE_BY_ID = loader.getSqlStatement("deleteCourseById");
        FIND_COURSE_NAME_BY_ID = loader.getSqlStatement("findCourseNameById");
    }

    protected JDBCCourseDao(Connection connection) {
        super(connection);
    }

    private void setCourseParameters(Course course, PreparedStatement createCourseStatement) throws SQLException {
        createCourseStatement.setString(1, course.getName());
        createCourseStatement.setDate(2, Date.valueOf(course.getStartDate()));
        createCourseStatement.setDate(3, Date.valueOf(course.getEndDate()));
        createCourseStatement.setInt(4, course.getTheme().getId());
        createCourseStatement.setString(5, course.getDescription());
    }

    @Override
    public void create(Course course) throws DBException {
        try (PreparedStatement createCourseStatement = connection.prepareStatement(CREATE_COURSE)) {
            setCourseParameters(course, createCourseStatement);
            createCourseStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public void createWithTutor(Course course) throws DBException{
        try (PreparedStatement createCourseStatement = connection.prepareStatement(CREATE_COURSE_WITH_TUTOR)) {
            setCourseParameters(course, createCourseStatement);
            createCourseStatement.setInt(6, course.getTutor().getId());
            createCourseStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public Course findById(int id) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_COURSE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new EntityNotFoundException();
            }
            return new CourseMapper().extractFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public Course findCourseNameById(int id) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_COURSE_NAME_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new EntityNotFoundException();
            }
            String name = resultSet.getString("name");
            return new Course.Builder()
                    .setId(id)
                    .setName(name)
                    .build();
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public List<Course> findAll() throws DBException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_COURSES)) {
            ResultSet resultSet = statement.executeQuery();
            CourseMapper mapper = new CourseMapper();
            return mapper.extractAsList(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public void update(Course course) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COURSE)) {
            setCourseParameters(course, preparedStatement);
            preparedStatement.setInt(6, course.getId());
            if (preparedStatement.executeUpdate() < 1) {
                throw new EntityNotFoundException();
             }
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public void updateWithTutor(Course course) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COURSE_WITH_TUTOR)) {
            setCourseParameters(course, preparedStatement);
            preparedStatement.setInt(6, course.getTutor().getId());
            preparedStatement.setInt(7, course.getId());
            if (preparedStatement.executeUpdate() < 1) {
                throw new EntityNotFoundException();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public void delete(int id) throws DBException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COURSE_BY_ID)) {
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() < 1) {
                throw new EntityNotFoundException();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.error(e);
            throw new IllegalDeletionException();
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
            logger.error(e);
            throw new DBException(e);
        }
    }

    public List<Course> getPageSortedByThemeName(int offset, int numberOfItems) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PAGE_SORTED_BY_THEME)) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, numberOfItems);
            ResultSet resultSet = preparedStatement.executeQuery();
            return new CourseMapper().extractAsList(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public int countCourses() throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_COURSES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }
}
