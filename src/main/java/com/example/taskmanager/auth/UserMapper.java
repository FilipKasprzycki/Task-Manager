package com.example.taskmanager.auth;

import com.example.taskmanager.db.entity.User;

class UserMapper {

    User map(String email, String password, String salt) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setSalt(salt);
        return user;
    }
}
