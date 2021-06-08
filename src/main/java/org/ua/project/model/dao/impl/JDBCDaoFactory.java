package org.ua.project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.*;
import org.ua.project.model.exception.DBException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory implements DaoFactory {
    Logger logger = LogManager.getLogger(JDBCDaoFactory.class);

    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() throws DBException {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public CourseDao createCourseDao() throws DBException {
        return new JDBCCourseDao(getConnection());
    }

    @Override
    public StudentCourseDao createStudentCourseDao() throws DBException {
        return new JDBCStudentCourseDao(getConnection());
    }

    @Override
    public ThemeDao createThemeDao() throws DBException {
        return new JDBCThemeDao(getConnection());
    }

    private Connection getConnection() throws DBException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }
}
