package augustopadilha.clientdistributedsystems.controllers;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordHashController {
    public static String passwordMD5(String password) {
        return DigestUtils.md5Hex(password).toUpperCase();
    }
}