package org.ua.project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.DBManager;
import org.ua.project.model.dao.UserDao;
import org.ua.project.model.dto.RegistrationData;
import org.ua.project.model.entity.UserRole;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class JDBCUserDao implements UserDao {
    Logger logger = LogManager.getLogger(JDBCUserDao.class);

    private static final String DEFAULT_ROLE = UserRole.USER.toString();

    private static final String INSERT_NEW_USER = "INSERT INTO users (fname, lname, login, password, role) " +
            "VALUES(?, ?, ?, ?, ?)";

    private static final long serialVersionUID = 3008758863898750550L;

    @Override
    public void createUser(RegistrationData data) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection conn = dbManager.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(INSERT_NEW_USER)) {
            preparedStatement.setString(1, data.getFirstName());
            preparedStatement.setString(2, data.getLastName());
            preparedStatement.setString(3, data.getLogin());
            preparedStatement.setString(4, data.getPassword());
            preparedStatement.setString(5, DEFAULT_ROLE);

            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntityAlreadyExistsException(e);
        } catch (SQLException e) {
            logger.warn(e);
            throw new DBException("Unexpected database error", e);
        }
    }
}
