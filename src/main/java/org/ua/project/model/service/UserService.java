package org.ua.project.model.service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.DaoFactory;
import org.ua.project.model.dao.UserDao;
import org.ua.project.model.dao.impl.JDBCDaoFactory;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.service.exception.ServiceException;
import org.ua.project.model.service.exception.WrongPasswordException;
import org.ua.project.model.service.util.encryption.EncryptionUtil;

import java.util.List;

/**
 * Service for user entities.
 */
public class UserService {
    Logger logger = LogManager.getLogger(UserService.class);

    /**
     * Registers specified user.
     * @param user - user to be registered.
     * @throws EntityAlreadyExistsException - if user with same login already exists.
     */
    public void registerUser(User user) throws EntityAlreadyExistsException {
        try (UserDao dao = new JDBCDaoFactory().createUserDao()) {
            user.setPassword(EncryptionUtil.encrypt(user.getPassword()));
            dao.createUser(user);
        } catch (EntityAlreadyExistsException e) {
            throw new EntityAlreadyExistsException();
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Updates user blocked status.
     * @param user - user entity with new blocked status.
     * @throws EntityNotFoundException - if specified user doesn't exist.
     */
    public void updateUserBlockedStatus(User user) throws EntityNotFoundException {
        try (UserDao jdbcUserDao = new JDBCDaoFactory().createUserDao()) {
            jdbcUserDao.updateUserBlockedStatus(user);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Attempts to sign in user.
     * @param login - user's login.
     * @param password - user's password.
     * @return User entity representing signed in user.
     * @throws EntityNotFoundException - if there is no user with such login.
     * @throws WrongPasswordException - if password doesn't match password of this account.
     */
    public User signInUser(String login, String password) throws EntityNotFoundException, WrongPasswordException {
        logger.info("User attempts {} attempts to sign in", login);
        try (UserDao dao = new JDBCDaoFactory().createUserDao()) {
           User user = dao.findUserByLogin(login);
           if (!EncryptionUtil.authenticate(password, user.getPassword())) {
               throw new WrongPasswordException();
           }
           return user;
       }  catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Gets list of all users for specified role.
     * @param role - User role.
     * @return list of users for specified role.
     */
    public List<User> getUsersByRole(User.Role role) {
        DaoFactory daoFactory = new JDBCDaoFactory();
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findUsersByRole(role);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * @return list of all users.
     */
    public List<User> findAllUsers() {
        try (UserDao userDao = new JDBCDaoFactory().createUserDao()) {
            return userDao.findAll();
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
