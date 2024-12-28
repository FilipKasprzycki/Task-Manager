package com.example.taskmanager.auth;

import com.example.taskmanager.db.entity.User;

class UserMapper {

    User map(AuthApiRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }
}
