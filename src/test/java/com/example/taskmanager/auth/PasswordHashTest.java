package com.example.taskmanager.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordHashTest {

    @Test
    void calculateHash() {
        assertEquals("6cc562fae10f53b5ffaa26d78d01ddecd7021191fbfc693b348d35b2a70aebb9f753b28ac36c285581ea6c841f036163660fe17c89c981400acc0927f3b655b9",
                new PasswordHash().calculateHash("password", "b608bd32994b3d44ec0d7172ded2c8cb"));
    }

}