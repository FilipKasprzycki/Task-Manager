package com.example.taskmanager.auth;

import com.example.taskmanager.db.entity.User;

class UserMapper {

    User map(AuthApiRequest request, String password, String salt) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(password);
        user.setSalt(salt);
        return user;
    }
}
