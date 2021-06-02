package org.ua.project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.CourseDao;
import org.ua.project.model.dao.mapper.CourseMapper;
import org.ua.project.model.dao.mapper.ThemeMapper;
import org.ua.project.model.dao.mapper.UserMapper;
import org.ua.project.model.entity.*;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;
import org.ua.project.model.util.SqlStatementLoader;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JDBCCourseDao extends JDBCAbstractDao implements CourseDao {
    private static Logger logger = LogManager.getLogger(JDBCCourseDao.class);

    private static final String FIND_ALL_COURSES;
    private static final String FIND_COURSE_BY_ID;
    private static final String CREATE_COURSE;
    private static final String CREATE_COURSE_WITH_TUTOR;
    private static final String UPDATE_COURSE;
    private static final String UPDATE_COURSE_WITH_TUTOR;
    private static final String DELETE_COURSE_BY_ID;
    private static final String FIND_COURSE_NAME_BY_ID;

    private static final String GET_PAGE_UNFILTERED;
    private static final String GET_PAGE_FILTER_BY_THEME;
    private static final String GET_PAGE_FILTER_BY_TUTOR;
    private static final String GET_PAGE_FILTER_BY_THEME_AND_TUTOR;

    private static final String SORT_BY_NAME_ASC;
    private static final String SORT_BY_NAME_DESC;
    private static final String SORT_BY_STUDENTS_ASC;
    private static final String SORT_BY_STUDENTS_DESC;
    private static final String SORT_BY_DURATION_ASC;
    private static final String SORT_BY_DURATION_DESC;

    private static final String COUNT_ALL_COURSES;
    private static final String COUNT_COURSES_BY_TUTOR;
    private static final String COUNT_COURSES_BY_THEME;
    private static final String COUNT_COURSES_BY_THEME_AND_TUTOR;


    static {
        SqlStatementLoader loader = SqlStatementLoader.getInstance();
        FIND_ALL_COURSES = loader.getSqlStatement("findAllCourses");
        CREATE_COURSE = loader.getSqlStatement("createCourse");
        CREATE_COURSE_WITH_TUTOR = loader.getSqlStatement("createCourseWithTutor");

        COUNT_ALL_COURSES = loader.getSqlStatement("countAllCourses");
        FIND_COURSE_BY_ID = loader.getSqlStatement("findCourseById");
        UPDATE_COURSE = loader.getSqlStatement("updateCourse");
        UPDATE_COURSE_WITH_TUTOR = loader.getSqlStatement("updateCourseWithTutor");
        DELETE_COURSE_BY_ID = loader.getSqlStatement("deleteCourseById");
        FIND_COURSE_NAME_BY_ID = loader.getSqlStatement("findCourseNameById");

        GET_PAGE_FILTER_BY_THEME = loader.getSqlStatement("getPageFilterByTheme");
        GET_PAGE_FILTER_BY_TUTOR = loader.getSqlStatement("getPageFilterByTutor");
        GET_PAGE_FILTER_BY_THEME_AND_TUTOR = loader.getSqlStatement("getPageFilterByThemeAndTutor");
        GET_PAGE_UNFILTERED = loader.getSqlStatement("getPageUnfiltered");

        SORT_BY_NAME_ASC = loader.getSqlStatement("sortByNameAsc");
        SORT_BY_NAME_DESC = loader.getSqlStatement("sortByNameDesc");
        SORT_BY_STUDENTS_ASC = loader.getSqlStatement("sortByStudentsAsc");
        SORT_BY_STUDENTS_DESC = loader.getSqlStatement("sortByStudentsDesc");
        SORT_BY_DURATION_ASC = loader.getSqlStatement("sortByDurationAsc");
        SORT_BY_DURATION_DESC = loader.getSqlStatement("sortByDurationDesc");

        COUNT_COURSES_BY_THEME = loader.getSqlStatement("countCoursesByTheme");
        COUNT_COURSES_BY_TUTOR = loader.getSqlStatement("countCoursesByTutor");
        COUNT_COURSES_BY_THEME_AND_TUTOR = loader.getSqlStatement("countCoursesByThemeAndTutor");
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
    public void createWithTutor(Course course) throws DBException {
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
            return getCoursesUniqueFields(statement);
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COURSE_BY_ID)) {
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
    public List<Course> getPageFilterByTheme(int offset, int numberOfItems, Theme theme) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PAGE_FILTER_BY_THEME)) {
            preparedStatement.setInt(1, theme.getId());
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, numberOfItems);
            return getCoursesUniqueFields(preparedStatement);
        } catch (SQLException e) {
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


    @Override
    public List<Course> getFilteredCoursePage(int offset, int numberOfItems,
                                              CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException {
       List<Course> page = new ArrayList<>();
        String orderByStatement =  getOrderByStatement(sortParameter);
       try (PreparedStatement preparedStatement = prepareFilteredPageStatement(offset, numberOfItems, filterOption, orderByStatement)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            UserMapper userMapper = new UserMapper();
            ThemeMapper themeMapper = new ThemeMapper();
            CourseMapper courseMapper = new CourseMapper();
            Map<Integer, Theme> themeCache = new HashMap<>();
            Map<Integer, User> userCache = new HashMap<>();
            while (resultSet.next()) {
                Course course = courseMapper.extractFromResultSet(resultSet);
                User tutor = userMapper.makeUnique(userCache, course.getTutor());
                Theme theme = themeMapper.makeUnique(themeCache, course.getTheme());
                course.setTheme(theme);
                course.setTutor(tutor);
                page.add(course);
            }
           return page;
       } catch (SQLException e) {
           logger.error(e);
           throw new DBException(e);
       }
    }

    @Override
    public int getFilteredCoursesCount(CourseFilterOption courseFilterOption) throws DBException{
        try (PreparedStatement preparedStatement = prepareFilteredCoursesCountStatement(courseFilterOption)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    private PreparedStatement prepareFilteredCoursesCountStatement(CourseFilterOption courseFilterOption) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            Optional<Theme> themeOpt = Optional.ofNullable(courseFilterOption.getTheme());
            Optional<User> tutorOpt = Optional.ofNullable(courseFilterOption.getTutor());


            if (themeOpt.isPresent() && tutorOpt.isPresent()) {
                preparedStatement = connection.prepareStatement(COUNT_COURSES_BY_THEME_AND_TUTOR);
                preparedStatement.setInt(1, themeOpt.get().getId());
                preparedStatement.setInt(2, tutorOpt.get().getId());
                return preparedStatement;
            }
            if (themeOpt.isPresent()) {
                preparedStatement = connection.prepareStatement(COUNT_COURSES_BY_THEME);
                preparedStatement.setInt(1, themeOpt.get().getId());
                return preparedStatement;
            }
            if (tutorOpt.isPresent()) {
                preparedStatement = connection.prepareStatement(COUNT_COURSES_BY_TUTOR);
                preparedStatement.setInt(1, tutorOpt.get().getId());
                return preparedStatement;
            }
            preparedStatement = connection.prepareStatement(COUNT_ALL_COURSES);
        } catch (SQLException e) {
            logger.error(e);
            if (preparedStatement != null) {
                preparedStatement.close();
                throw e;
            }
        }
        return preparedStatement;
    }

    private PreparedStatement prepareFilteredPageStatement(int offset, int numberOfItems, CourseFilterOption courseFilterOption, String orderByStatement) throws SQLException {
        Optional<User> tutorOpt = Optional.ofNullable(courseFilterOption.getTutor());
        Optional<Theme> themeOpt = Optional.ofNullable(courseFilterOption.getTheme());

        String sql;
        PreparedStatement preparedStatement = null;

        try {
            if (tutorOpt.isPresent() && themeOpt.isPresent()) {
                sql = String.format(GET_PAGE_FILTER_BY_THEME_AND_TUTOR, orderByStatement);
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, themeOpt.get().getId());
                preparedStatement.setInt(2, tutorOpt.get().getId());
                preparedStatement.setInt(3, offset);
                preparedStatement.setInt(4, numberOfItems);
                return preparedStatement;
            }
            if (themeOpt.isPresent()) {
                sql = String.format(GET_PAGE_FILTER_BY_THEME, orderByStatement);
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, themeOpt.get().getId());
                preparedStatement.setInt(2, offset);
                preparedStatement.setInt(3, numberOfItems);
                return preparedStatement;
            }
            if (tutorOpt.isPresent()) {
                sql = String.format(GET_PAGE_FILTER_BY_TUTOR, orderByStatement);
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, tutorOpt.get().getId());
                preparedStatement.setInt(2, offset);
                preparedStatement.setInt(3, numberOfItems);
                return preparedStatement;
            }

            sql = String.format(GET_PAGE_UNFILTERED, orderByStatement);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, numberOfItems);
        } catch (SQLException e) {
            logger.error(e);
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            throw  e;
        }
        return preparedStatement;
    }

    private String getOrderByStatement(CourseSortParameter sortParameter) {
        String orderByStatement;
        if (CourseSortParameter.BY_NAME_DESC.equals(sortParameter)) {
            orderByStatement = SORT_BY_NAME_DESC;
        } else if (CourseSortParameter.BY_DURATION_ASC.equals(sortParameter)) {
            orderByStatement = SORT_BY_DURATION_ASC;
        } else if (CourseSortParameter.BY_DURATION_DESC.equals(sortParameter)) {
            orderByStatement = SORT_BY_DURATION_DESC;
        } else if (CourseSortParameter.BY_STUDENTS_ASC.equals(sortParameter)) {
            orderByStatement = SORT_BY_STUDENTS_ASC;
        } else if (CourseSortParameter.BY_STUDENTS_DESC.equals(sortParameter)) {
            orderByStatement = SORT_BY_STUDENTS_DESC;
        } else {
            orderByStatement = SORT_BY_NAME_ASC;
        }
        return orderByStatement;
    }

    private List<Course> getCoursesUniqueFields(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Course> courseList = new ArrayList<>();
        Map<Integer, Theme> themeCache = new HashMap<>();
        Map<Integer, User> userCache = new HashMap<>();
        CourseMapper courseMapper = new CourseMapper();
        ThemeMapper themeMapper = new ThemeMapper();
        UserMapper userMapper = new UserMapper();
        while (resultSet.next()) {
            Course course = courseMapper.extractFromResultSet(resultSet);
            Theme theme = themeMapper.makeUnique(themeCache, course.getTheme());

            course.setTheme(theme);
            Optional.ofNullable(course.getTutor()).ifPresent(tutor ->
                    course.setTutor(userMapper.makeUnique(userCache, tutor)));
            courseList.add(course);
        }
        return courseList;
    }

    @Override
    public int countCourses() throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_COURSES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }
}
