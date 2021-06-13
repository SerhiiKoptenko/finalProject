package org.ua.project.model.dao;

import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.model.exception.EntityNotFoundException;

import java.util.List;

public interface UserDao extends Dao {

    /**
     * Creates user specified by User entity.
     * @param user - User to be created.
     * @throws DBException - if database error occurs.
     * @throws EntityAlreadyExistsException - if user with such login already exists.
     */
    void createUser(User user) throws DBException, EntityAlreadyExistsException;

    /**
     * Finds all users.
     * @return list of all users.
     * @throws DBException - if database error occurs.
     */
    List<User> findAll() throws DBException;

    /**
     * Finds all users with specified role.
     * @param role - role for which users should be found.
     * @return list of users.
     * @throws DBException - if database error occurs.
     */
    List<User> findUsersByRole(User.Role role) throws DBException;

    /**
     * Finds user by login.
     * @param login - user login.
     * @return User entity.
     * @throws DBException - if database error occurs.
     * @throws EntityNotFoundException - if there is no user with specified login.
     */
    User findUserByLogin(String login) throws DBException, EntityNotFoundException;

    /**
     * Updates user blocked status.
     * @param user - user for which status should be updated.
     * @throws DBException - if database error occurs.
     * @throws EntityNotFoundException - if there is no user with specified login.
     */
    void updateUserBlockedStatus(User user) throws DBException, EntityNotFoundException;
}
