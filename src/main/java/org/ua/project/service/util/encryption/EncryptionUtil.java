package org.ua.project.service.util.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {

    public static String encrypt(String line) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(line.getBytes());
            return toHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHexString(byte[] bytes) {
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2];
        int b;
        for (int j = 0; j < bytes.length; j++) {
            b = bytes[j] & 0xFF;
            hexChars[j * 2] = hexDigits[b / 16];
            hexChars[j * 2 + 1] = hexDigits[b % 16];
        }
        return new String(hexChars);
    }
}
