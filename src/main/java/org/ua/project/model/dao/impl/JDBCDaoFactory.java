package org.ua.project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.CourseDao;
import org.ua.project.model.dao.DaoFactory;
import org.ua.project.model.dao.ThemeDao;
import org.ua.project.model.dao.UserDao;
import org.ua.project.model.exception.DBException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory implements DaoFactory {
    Logger logger = LogManager.getLogger(JDBCDaoFactory.class);

    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() {
        try {
            return new JDBCUserDao(dataSource.getConnection());
        }  catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public CourseDao createCourseDao() {
        try {
            return new JDBCCourseDao(dataSource.getConnection());
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ThemeDao createThemeDao() {
        try {
            return new JDBCThemeDao(dataSource.getConnection());
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
