package org.ua.project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.CourseDao;
import org.ua.project.model.dao.impl.mapper.CourseMapper;
import org.ua.project.model.entity.Course;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.util.SqlStatementLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCCourseDao extends JDBCAbstractDao implements CourseDao {
    private static Logger logger = LogManager.getLogger(JDBCCourseDao.class);

    private final static String FIND_ALL_COURSES;

    static {
        SqlStatementLoader loader = new SqlStatementLoader("sqlStatements.properties");
        FIND_ALL_COURSES = loader.getSqlStatement("findAllCourses");
    }

    protected JDBCCourseDao (Connection connection) {
        super(connection);
    }

    @Override
    public void create(Course entity) throws DBException {

    }

    @Override
    public Course findById(int id) {
        return null;
    }

    @Override
    public List<Course> findAll() {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_COURSES)) {
            ResultSet resultSet = statement.executeQuery();
            CourseMapper mapper = new CourseMapper();
            return mapper.extractCoursesAsList(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public void update(Course entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
