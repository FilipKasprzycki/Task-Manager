package com.example.taskmanager.auth;

import com.example.taskmanager.auth.entity.AuthApiRequest;
import jakarta.inject.Inject;

public class AuthService {

    @Inject
    private UserRegisterService registerService;

    @Inject
    private UserLoginService loginService;

    public void register(AuthApiRequest request) {
        registerService.register(request);
    }

    public String login(AuthApiRequest request) {
        return loginService.login(request);
    }
}
