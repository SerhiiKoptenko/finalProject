package org.ua.project.model.service.util.encryption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 * Utility class for password encryption and authentication.
 */
public class EncryptionUtil {
    public static final int SALT_LENGTH = 16;
    public static final int ITERATION_COUNT = 65536;
    public static final int KEY_LENGTH = 128;

    private static final Logger logger = LogManager.getLogger(EncryptionUtil.class);

    //suppress default constructor
    private EncryptionUtil() {
        throw new AssertionError();
    }

    /**
     * Encrypts given password using PBKDF2 with randomly generated salt.
     *
     * @param password - password to be encrypted.
     * @return encrypted password as hexadecimal string.
     */
    public static String encrypt(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        byte[] hash = hashPassword(password, salt);
        return getHashWithSalt(salt, hash);
    }

    /**
     * Hashes given password and compares result with given encrypted password.
     * @param password - input password to compare.
     * @param encryptedPassword - hashed password to be compared with.
     * @return true if passwords match, false otherwise.
     */
    public static boolean authenticate(String password, String encryptedPassword) {
        byte[] salt = fromHexString(encryptedPassword.substring(0, SALT_LENGTH * 2));
        byte[] hash = fromHexString(encryptedPassword.substring(SALT_LENGTH * 2));

        byte[] inputHash = hashPassword(password, salt);
        return Arrays.equals(hash, inputHash);
    }

    private static byte[] hashPassword(String password, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    private static String getHashWithSalt(byte[] salt, byte[] hash) {
        byte[] hashWithSalt = new byte[salt.length + hash.length];
        System.arraycopy(salt, 0, hashWithSalt, 0, salt.length);
        System.arraycopy(hash, 0, hashWithSalt, salt.length, hash.length);
        return toHexString(hashWithSalt);
    }

    private static String toHexString(byte[] bytes) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int b;
        for (int j = 0; j < bytes.length; j++) {
            b = bytes[j] & 0xFF;
            hexChars[j * 2] = hexDigits[b / 16];
            hexChars[j * 2 + 1] = hexDigits[b % 16];
        }
        return new String(hexChars);
    }

    private static byte[] fromHexString(String hex) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = hex.toCharArray();

        byte[] bytes = new byte[hexChars.length / 2];
        for (int i = 0; i < bytes.length; i++) {
            int digit = 0;
            while (hexDigits[digit] != hexChars[i * 2]) {
                digit++;
            }
            int firstQuarter = digit * 16;
            digit = 0;
            while (hexDigits[digit] != hexChars[i * 2 + 1]) {
                digit++;
            }
            int secondQuartet = digit;
            bytes[i] = (byte) (firstQuarter + secondQuartet);
        }
        return bytes;
    }
}
