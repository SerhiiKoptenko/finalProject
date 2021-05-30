package org.ua.project.model.dao;

import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;

import java.util.List;

public interface UserDao extends GenericDao<User> {

    User findByLogin(String login) throws DBException;

    User getAuthenticationData(String login) throws DBException, EntityNotFoundException;

    List<User> getUsersByRole(User.Role role) throws DBException;
}
