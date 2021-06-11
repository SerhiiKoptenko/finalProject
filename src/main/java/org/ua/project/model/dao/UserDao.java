package org.ua.project.model.dao;

import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.model.exception.EntityNotFoundException;

import java.util.List;

public interface UserDao extends Dao {

    void createUser(User user) throws DBException, EntityAlreadyExistsException;

    List<User> findAll() throws DBException;

    List<User> getUsersByRole(User.Role role) throws DBException;

    User getUserByLogin(String login) throws DBException, EntityNotFoundException;

    boolean updateUserBlockedStatus(User user) throws DBException, EntityNotFoundException;
}
