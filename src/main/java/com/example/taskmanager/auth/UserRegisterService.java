package com.example.taskmanager.auth;

import com.example.taskmanager.auth.entity.AuthApiRequest;
import com.example.taskmanager.auth.exception.UserRegisterException;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.UserManager;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
class UserRegisterService {

    @Inject
    private UserManager userManager;

    @Inject
    private UserMapper userMapper;

    @Inject
    private PasswordHash passwordHash;

    void register(AuthApiRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        verifyThatUserNotExists(email);

        String salt = passwordHash.getSalt();
        String hashedPassword = passwordHash.calculateHash(request.getPassword(), salt);

        User user = userMapper.map(request, hashedPassword, salt);
        userManager.persist(user);
    }

    private void verifyThatUserNotExists(String email) {
        Optional<User> optionalUser = userManager.findByEmail(email);

        if (optionalUser.isPresent()) {
            throw new UserRegisterException("Can not create new user, because user with email [" + email + "] already exists. Email must be unique.");
        }
    }
}
