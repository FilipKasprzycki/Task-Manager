package com.example.taskmanager.auth;

import com.example.taskmanager.auth.entity.AuthApiRequest;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    @Inject
    private UserRegisterService registerService;

    @Inject
    private UserLoginService loginService;

    public void register(AuthApiRequest request) {
        registerService.register(request);
    }

    public void login(AuthApiRequest request) {
        loginService.login(request);
    }
}
