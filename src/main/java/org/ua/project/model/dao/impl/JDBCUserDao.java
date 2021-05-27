package org.ua.project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.UserDao;
import org.ua.project.model.dao.impl.mapper.UserMapper;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.util.SqlStatementLoader;

import java.sql.*;
import java.util.List;

public class JDBCUserDao extends JDBCAbstractDao implements UserDao {
    private static final Logger logger = LogManager.getLogger(JDBCUserDao.class);

    private static final String DEFAULT_ROLE = User.Role.USER.toString();

    private static final String INSERT_NEW_USER;
    private static final String GET_AUTHORIZATION_DATA;
    private static final String GET_USERS_BY_ROLE;

    private static final long serialVersionUID = 3008758863898750550L;

    static {
        SqlStatementLoader sqlStatementLoader = new SqlStatementLoader("sqlStatements.properties");
        INSERT_NEW_USER = sqlStatementLoader.getSqlStatement("insertNewUser");
        GET_AUTHORIZATION_DATA = sqlStatementLoader.getSqlStatement("getAuthorizationData");
        GET_USERS_BY_ROLE = sqlStatementLoader.getSqlStatement("getUsersByRole");
    }

    protected JDBCUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<User> getUsersByRole(User.Role role) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_BY_ROLE)) {
            preparedStatement.setString(1, role.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return new UserMapper().extractAsList(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public void create(User user) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, DEFAULT_ROLE);

            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntityAlreadyExistsException();
        } catch (SQLException e) {
            logger.warn(e);
            throw new DBException("Unexpected database error", e);
        }
    }

    @Override
    public User findByLogin(String login) throws DBException {
        return null;
    }

    @Override
    public User getAuthenticationData(String login) throws DBException, EntityNotFoundException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHORIZATION_DATA)) {
           preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

           if (!resultSet.next()) {
               throw new EntityNotFoundException();
           }

           UserMapper mapper = new UserMapper();
           User user = mapper.extractAuthorizationData(resultSet);
           user.setLogin(login);
           return user;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DBException("Unexpected database error", e);
        }
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
