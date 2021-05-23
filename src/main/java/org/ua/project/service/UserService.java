package org.ua.project.service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dto.RegistrationData;
import org.ua.project.model.dao.impl.JDBCUserDao;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.service.exception.EncryptionException;
import org.ua.project.service.util.encryption.EncryptionUtil;

import java.security.NoSuchAlgorithmException;

public class UserService {
    Logger logger = LogManager.getLogger(UserService.class);

    public void registerUser(RegistrationData data) throws EncryptionException, DBException {
        String encryptedPass = null;
        try {
            encryptedPass = encryptPassword(data.getPassword());
        } catch (NoSuchAlgorithmException e) {
            logger.error("Hashing algorithm not found");
            throw new EncryptionException(e);
        }
        data.setPassword(encryptedPass);
        JDBCUserDao dao = new JDBCUserDao();
        dao.createUser(data);
    }

    private String encryptPassword(String password) throws NoSuchAlgorithmException {
        return EncryptionUtil.encrypt(password);
    }

    protected UserService() {}
}
