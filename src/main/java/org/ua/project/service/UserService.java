package org.ua.project.service;
import org.ua.project.model.dto.RegistrationData;
import org.ua.project.model.dao.impl.JDBCUserDao;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;

public class UserService {

    public void registerUser(RegistrationData data) throws EntityAlreadyExistsException, DBException {
        JDBCUserDao dao = new JDBCUserDao();
        dao.createUser(data);
    }

    protected UserService() {}
}
