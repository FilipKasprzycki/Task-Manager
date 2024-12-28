package com.example.taskmanager.auth;

import jakarta.inject.Inject;

public class AuthService {

    @Inject
    private AuthRegisterService authRegisterService;

    public void register(AuthApiRequest request) {
        authRegisterService.register(request);
    }
}
