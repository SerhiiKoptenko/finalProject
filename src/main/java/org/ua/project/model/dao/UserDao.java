package org.ua.project.model.dao;

import org.ua.project.model.dto.RegistrationData;
import org.ua.project.model.exception.DBException;

import java.io.Serializable;

public interface UserDao extends Serializable {
    void createUser(RegistrationData data) throws DBException;
}
