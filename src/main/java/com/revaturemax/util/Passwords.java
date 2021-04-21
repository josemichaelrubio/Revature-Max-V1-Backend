package com.revaturemax.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class Passwords {

    public static byte[] getNewPasswordSalt() {
        byte[] passwordSalt = new byte[32];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(passwordSalt);
        return passwordSalt;
    }

    public static byte[] getPasswordHash(String password, byte[] passwordSalt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(passwordSalt);
            return md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
