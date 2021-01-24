package com.pozharsky.dmitri.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncrypter {

    public static String encryptPassword(String password) {
        String salt = BCrypt.gensalt();
        String hashPassword = BCrypt.hashpw(password, salt);
        return hashPassword;
    }

    public static boolean checkPassword(String password, String hashPassword) {
        boolean result = BCrypt.checkpw(password, hashPassword);
        return result;
    }
}
