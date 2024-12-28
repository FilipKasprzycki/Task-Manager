package com.example.taskmanager.auth;

import com.example.taskmanager.api.exception.Http400BadRequestException;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.UserManager;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
class AuthRegisterService {

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

        User user = userMapper.map(request);
        userManager.persist(user);
    }
}
