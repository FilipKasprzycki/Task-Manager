package com.example.taskmanager.auth;

import com.example.taskmanager.api.exception.Http400BadRequestException;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.UserManager;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.lang.util.ByteSource;

import java.util.Optional;

@Slf4j
class AuthRegisterService {

    private static final String PEPPER = "4x#i$@hi%EWY#D8d:;45}#=]/9L+F!?$";

    @Inject
    private UserManager userManager;

    @Inject
    private UserMapper userMapper;

    void register(AuthApiRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        Optional<User> optionalUser = userManager.findByEmail(email);

        if (optionalUser.isPresent()) {
            log.warn("Can not create new user, because user with email [{}] already exists. Email must be unique.", email);
            throw new Http400BadRequestException("User already exists");
        }

        ByteSource salt = new SecureRandomNumberGenerator().nextBytes();
        String hashedPassword = new Sha512Hash(PEPPER + request.getPassword(), salt, 2_000_000).toHex();

        User user = userMapper.map(request, hashedPassword, salt.toHex());
        userManager.persist(user);
    }
}
