package org.ua.project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.UserDao;
import org.ua.project.model.dao.mapper.UserMapper;
import org.ua.project.model.entity.Course;
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
    private static final String GET_USER_ID_BY_LOGIN;
    private static final String ENROLL_USER;
    private static final String CREATE_USER_JOURNAl;

    private static final long serialVersionUID = 3008758863898750550L;

    static {
        SqlStatementLoader sqlStatementLoader = SqlStatementLoader.getInstance();
        INSERT_NEW_USER = sqlStatementLoader.getSqlStatement("insertNewUser");
        GET_AUTHORIZATION_DATA = sqlStatementLoader.getSqlStatement("getAuthorizationData");
        GET_USERS_BY_ROLE = sqlStatementLoader.getSqlStatement("getUsersByRole");
        GET_USER_ID_BY_LOGIN = sqlStatementLoader.getSqlStatement("getUserIdByLogin");
        ENROLL_USER = sqlStatementLoader.getSqlStatement("enrollUser");
        CREATE_USER_JOURNAl = sqlStatementLoader.getSqlStatement("createUserJournal");
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
            logger.error(e);
            throw new DBException("Unexpected database error", e);
        }
    }

    @Override
    public User getUserByLogin(String login) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ID_BY_LOGIN)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new EntityNotFoundException();
            }
            return new UserMapper().extractFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
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
    public User findById(int id) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("")) {
            preparedStatement.setInt(1, id);
            return null;
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public User enrollStudent(String login, Course course) throws DBException {
        try (PreparedStatement enrollStudentStatement = connection.prepareStatement(ENROLL_USER);
             PreparedStatement createJournalStatement = connection.prepareStatement(CREATE_USER_JOURNAl)) {
            connection.setAutoCommit(false);
            User user = getUserByLogin(login);
            enrollStudentStatement.setInt(1, user.getId());
            enrollStudentStatement.setInt(2, course.getId());
            createJournalStatement.setInt(1, user.getId());
            createJournalStatement.setInt(2, course.getId());
            enrollStudentStatement.executeUpdate();
            createJournalStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DBException(e);
            } catch (SQLException ex) {
                logger.error(ex);
                throw new DBException(ex);
            }
        }
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
