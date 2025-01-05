package com.example.taskmanager.auth;

import com.example.taskmanager.db.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    @Test
    void map() {
        User expected = new User();
        expected.setEmail("jan.kowalski@gmail.com");
        expected.setPassword("Password1!");
        expected.setSalt("b608bd32994b3d44ec0d7172ded2c8cb");

        User user = new UserMapper().map("jan.kowalski@gmail.com", "Password1!", "b608bd32994b3d44ec0d7172ded2c8cb");
        assertEquals(expected, user);
    }
}