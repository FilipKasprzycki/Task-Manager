package com.example.taskmanager.auth;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;

class PasswordHash {

    private static final String PEPPER = "4x#i$@hi%EWY#D8d:;45}#=]/9L+F!?$";

    String calculateHash(String password, String salt) {
        return new Sha512Hash(PEPPER + password, salt, 2_000_000).toHex();
    }

    String getSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }
}
