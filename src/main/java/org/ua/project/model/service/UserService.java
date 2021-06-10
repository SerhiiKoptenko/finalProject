package org.ua.project.model.service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.DaoFactory;
import org.ua.project.model.dao.StudentCourseDao;
import org.ua.project.model.dao.UserDao;
import org.ua.project.model.dao.impl.JDBCDaoFactory;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalInsertionException;
import org.ua.project.model.service.exception.ServiceException;
import org.ua.project.model.service.exception.UserAlreadyExistsException;
import org.ua.project.model.service.exception.WrongPasswordException;
import org.ua.project.model.service.util.encryption.EncryptionUtil;

import java.util.List;

public class UserService {
    Logger logger = LogManager.getLogger(UserService.class);

    public void addUser(User user) throws ServiceException, EntityAlreadyExistsException {
        try (UserDao dao = new JDBCDaoFactory().createUserDao()) {
            dao.createUser(user);
        } catch (EntityAlreadyExistsException e) {
            throw new EntityAlreadyExistsException();
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public boolean updateUserBlockedStatus(User user) {
        try (UserDao jdbcUserDao = new JDBCDaoFactory().createUserDao()) {
            return jdbcUserDao.updateUserBlockedStatus(user);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public void enrollStudent(int studId, int courseId) throws IllegalInsertionException, EntityNotFoundException {
        try (StudentCourseDao studentCourseDao = new JDBCDaoFactory().createStudentCourseDao()) {
            studentCourseDao.enrollStudent(studId, courseId);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public User signInUser(String login, String password) throws EntityNotFoundException, WrongPasswordException {
        String encryptedPassword = EncryptionUtil.encrypt(password);
        logger.trace("User attempts to sign in with  the following data: {} {} ",login, encryptedPassword);
        try (UserDao dao = new JDBCDaoFactory().createUserDao()) {
           User user = dao.getUserByLogin(login);
           if (!user.getPassword().equals(encryptedPassword)) {
               throw new WrongPasswordException();
           }
           return user;
       }  catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public List<User> getUsersByRole(User.Role role) {
        DaoFactory daoFactory = new JDBCDaoFactory();
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.getUsersByRole(role);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public List<User> findAllUsers() {
        try (UserDao userDao = new JDBCDaoFactory().createUserDao()) {
            return userDao.findAll();
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
