package com.example.taskmanager.auth;

import com.example.taskmanager.auth.entity.AuthApiRequest;
import com.example.taskmanager.auth.exception.UserLoginException;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.UserManager;
import com.example.taskmanager.jwt.JwtCreator;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class UserLoginService {

    @Inject
    private UserManager userManager;

    @Inject
    private PasswordHash passwordHash;

    @Inject
    private JwtCreator jwtCreator;

    String login(AuthApiRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        User user = userManager.findByEmail(email)
                .orElseThrow(() -> new UserLoginException("Not found user by email: [" + email + "]"));

        String hashCalculatedFromGivenPassword = passwordHash.calculateHash(request.getPassword(), user.getSalt());

        if (!hashCalculatedFromGivenPassword.equals(user.getPassword())) {
            throw new UserLoginException("Incorrect password for user [" + email + "]");
        }

        log.info("User [{}] successfully logged", email);
        return jwtCreator.createToken(email);
    }
}
