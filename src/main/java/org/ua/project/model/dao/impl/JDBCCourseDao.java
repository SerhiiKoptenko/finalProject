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
    private static final Logger logger = LogManager.getLogger(JDBCCourseDao.class);

    private static final String FIND_ALL_COURSES;
    private static final String FIND_COURSE_BY_ID;
    private static final String CREATE_COURSE;
    private static final String CREATE_COURSE_WITH_TUTOR;
    private static final String UPDATE_COURSE;
    private static final String UPDATE_COURSE_WITH_TUTOR;
    private static final String DELETE_COURSE_BY_ID;
    private static final String FIND_COURSE_NAME_BY_ID;

    public static final String GET_FILTERED_COURSES;
    private static final String GET_FILTERED_COURSE_PAGE;
    private static final String GET_PAGE_FILTER_BY_THEME;

    private static final String SORT_BY_NAME_ASC;
    private static final String SORT_BY_NAME_DESC;
    private static final String SORT_BY_STUDENTS_ASC;
    private static final String SORT_BY_STUDENTS_DESC;
    private static final String SORT_BY_DURATION_ASC;
    private static final String SORT_BY_DURATION_DESC;

    private static final String COUNT_ALL_COURSES;
    private static final String COUNT_COURSES_FILTERED;
    public static final String FIND_ONGOING;
    public static final String FIND_NOT_STARTED;
    public static final String FIND_COMPLETED;


    static {
        SqlStatementLoader loader = SqlStatementLoader.getInstance();
        FIND_ALL_COURSES = loader.getSqlStatement("findAllCourses");
        CREATE_COURSE = loader.getSqlStatement("createCourse");
        CREATE_COURSE_WITH_TUTOR = loader.getSqlStatement("createCourseWithTutor");

        FIND_COURSE_BY_ID = loader.getSqlStatement("findCourseById");
        UPDATE_COURSE = loader.getSqlStatement("updateCourse");
        UPDATE_COURSE_WITH_TUTOR = loader.getSqlStatement("updateCourseWithTutor");
        DELETE_COURSE_BY_ID = loader.getSqlStatement("deleteCourseById");
        FIND_COURSE_NAME_BY_ID = loader.getSqlStatement("findCourseNameById");

        GET_PAGE_FILTER_BY_THEME = loader.getSqlStatement("getPageFilterByTheme");

        SORT_BY_NAME_ASC = loader.getSqlStatement("sortByNameAsc");
        SORT_BY_NAME_DESC = loader.getSqlStatement("sortByNameDesc");
        SORT_BY_STUDENTS_ASC = loader.getSqlStatement("sortByStudentsAsc");
        SORT_BY_STUDENTS_DESC = loader.getSqlStatement("sortByStudentsDesc");
        SORT_BY_DURATION_ASC = loader.getSqlStatement("sortByDurationAsc");
        SORT_BY_DURATION_DESC = loader.getSqlStatement("sortByDurationDesc");

        COUNT_ALL_COURSES = loader.getSqlStatement("countAllCourses");
        COUNT_COURSES_FILTERED = loader.getSqlStatement("countCoursesFiltered");

        GET_FILTERED_COURSES = loader.getSqlStatement("getFilteredCourses");
        GET_FILTERED_COURSE_PAGE = loader.getSqlStatement("getAvailableCoursesPage");

        FIND_ONGOING = loader.getSqlStatement("findOngoing");
        FIND_NOT_STARTED = loader.getSqlStatement("findNotStarted");
        FIND_COMPLETED = loader.getSqlStatement("findCompleted");
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
    public List<Course> getFilteredCourses(CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException {
        List<Course> courses = new ArrayList<>();
        String filterByStatus = getFilterByStatusStatement(filterOption);
        String sql = String.format(GET_FILTERED_COURSES, filterByStatus, getOrderByStatement(sortParameter));
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            prepareFilterStatement(filterOption, preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            CourseMapper courseMapper = new CourseMapper();
            UserMapper userMapper = new UserMapper();
            ThemeMapper themeMapper = new ThemeMapper();

            Map<Integer, Theme> themeCache = new HashMap<>();
            Map<Integer, User> tutorCache = new HashMap<>();
            while (resultSet.next()) {
                Course course = courseMapper.extractFromResultSet(resultSet);
                User tutor = userMapper.makeUnique(tutorCache, course.getTutor());
                Theme theme = themeMapper.makeUnique(themeCache, course.getTheme());
                course.setTheme(theme);
                course.setTutor(tutor);
                courses.add(course);
            }
            return courses;
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    private void prepareFilterStatement(CourseFilterOption filterOption, PreparedStatement preparedStatement) throws SQLException {
        int themeId = 0;
        int tutorId = 0;
        int studId = 0;

        Optional<Theme> themeOpt = Optional.ofNullable(filterOption.getTheme());
        Optional<User> tutorOpt = Optional.ofNullable(filterOption.getTutor());
        Optional<User> studOpt = Optional.ofNullable(filterOption.getStudent());

        if (themeOpt.isPresent()) {
            themeId = themeOpt.get().getId();
        }
        if (tutorOpt.isPresent()) {
            tutorId = tutorOpt.get().getId();
        }
        if (studOpt.isPresent()) {
            studId = studOpt.get().getId();
        }

        preparedStatement.setInt(1, themeId);
        preparedStatement.setInt(2, themeId);
        preparedStatement.setInt(3, tutorId);
        preparedStatement.setInt(4, tutorId);
        preparedStatement.setInt(5, studId);
        preparedStatement.setInt(6, studId);
    }

    @Override
    public List<Course> getFilteredCourses(int offset, int numberOfItems,
                                           CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException {
        List<Course> page = new ArrayList<>();
        String filterByStatus = getFilterByStatusStatement(filterOption);
        String sql = String.format(GET_FILTERED_COURSE_PAGE, filterByStatus, getOrderByStatement(sortParameter));
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            prepareFilterStatement(filterOption, preparedStatement);

            preparedStatement.setInt(7, offset);
            preparedStatement.setInt(8, numberOfItems);

            ResultSet resultSet = preparedStatement.executeQuery();
            CourseMapper courseMapper = new CourseMapper();
            UserMapper userMapper = new UserMapper();
            ThemeMapper themeMapper = new ThemeMapper();

            Map<Integer, Theme> themeCache = new HashMap<>();
            Map<Integer, User> tutorCache = new HashMap<>();
            while (resultSet.next()) {
                Course course = courseMapper.extractFromResultSet(resultSet);
                User tutor = userMapper.makeUnique(tutorCache, course.getTutor());
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


    private String getFilterByStatusStatement(CourseFilterOption courseFilterOption) {
        CourseFilterOption.CourseStatus courseStatus = courseFilterOption.getCourseStatus();
        if (CourseFilterOption.CourseStatus.COMPLETED.equals(courseStatus)) {
            return FIND_COMPLETED;
        } else if (CourseFilterOption.CourseStatus.NOT_STARTED.equals(courseStatus)) {
            return FIND_NOT_STARTED;
        } else if (CourseFilterOption.CourseStatus.ONGOING.equals(courseStatus)) {
            return FIND_ONGOING;
        }
        return "0 = 0";
    }

    @Override
    public int getFilteredCoursesCount(CourseFilterOption courseFilterOption) throws DBException {
        String filterByStatusStatement = getFilterByStatusStatement(courseFilterOption);
        String sql = String.format(COUNT_COURSES_FILTERED, filterByStatusStatement);
        logger.debug("constructed query: {}", sql);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            Optional<Theme> themeOpt = Optional.ofNullable(courseFilterOption.getTheme());
            Optional<User> tutorOpt = Optional.ofNullable(courseFilterOption.getTutor());
            int themeId = 0;
            int tutorId = 0;
            if (themeOpt.isPresent()) {
                themeId = themeOpt.get().getId();
            }
            if (tutorOpt.isPresent()) {
                tutorId = tutorOpt.get().getId();
            }
            preparedStatement.setInt(1, themeId);
            preparedStatement.setInt(2, themeId);
            preparedStatement.setInt(3, tutorId);
            preparedStatement.setInt(4, tutorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
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
