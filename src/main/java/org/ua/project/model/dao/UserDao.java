package org.ua.project.model.dao;

import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;

import java.util.List;

public interface UserDao extends GenericDao<User> {

    List<User> getUsersByRole(User.Role role) throws DBException;

    User getUserByLogin(String login) throws DBException;

    boolean updateUserBlockedStatus(User user) throws DBException;
}
