package org.ua.project.model.dao;

import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;

import java.util.List;

public interface UserDao extends Dao {

    User findById(int id) throws DBException;

    void create(User user) throws DBException;

    List<User> findAll() throws DBException;

    List<User> getUsersByRole(User.Role role) throws DBException;

    User getUserByLogin(String login) throws DBException;

    boolean updateUserBlockedStatus(User user) throws DBException;
}
