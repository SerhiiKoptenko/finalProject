package org.ua.project.service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.DaoFactory;
import org.ua.project.model.dao.UserDao;
import org.ua.project.model.dao.impl.JDBCDaoFactory;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.service.exception.NoSuchUserException;
import org.ua.project.service.exception.ServiceException;
import org.ua.project.service.exception.UserAlreadyExistsException;
import org.ua.project.service.exception.WrongPasswordException;
import org.ua.project.service.util.encryption.EncryptionUtil;

import java.util.List;

public class UserService {
    Logger logger = LogManager.getLogger(UserService.class);

    public void addUser(User user) throws ServiceException, UserAlreadyExistsException{
        try (UserDao dao = new JDBCDaoFactory().createUserDao()) {
            dao.create(user);
        } catch (EntityAlreadyExistsException e) {
            throw new UserAlreadyExistsException();
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public User signInUser(String login, String password) throws EntityNotFoundException, WrongPasswordException {
        String encryptedPassword = EncryptionUtil.encrypt(password);
        logger.trace("User attempts to sign in with  the following data: {} {} ",login, encryptedPassword);
        try (UserDao dao = new JDBCDaoFactory().createUserDao()) {
           User user = dao.getAuthenticationData(login);
           if (!user.getPassword().equals(encryptedPassword)) {
               throw new WrongPasswordException();
           }
           return user;
       }
    }

    public List<User> getUsersByRole(User.Role role) {
        DaoFactory daoFactory = new JDBCDaoFactory();

        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.getUsersByRole(role);
        }
    }
}
