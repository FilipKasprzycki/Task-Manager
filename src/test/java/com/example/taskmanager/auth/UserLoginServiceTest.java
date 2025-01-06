package com.example.taskmanager.auth;

import com.example.taskmanager.auth.entity.AuthApiRequest;
import com.example.taskmanager.auth.exception.UserLoginException;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.UserManager;
import com.example.taskmanager.jwt.JwtCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserLoginServiceTest {

    private AutoCloseable closable;

    @Mock
    private UserManager userManager;

    @Mock
    private PasswordHash passwordHash;

    @Mock
    private JwtCreator jwtCreator;

    @InjectMocks
    private UserLoginService sut;

    @BeforeEach
    void setUp() {
        closable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closable.close();
    }

    @Test
    void login_emailNotFound() {
        // given
        AuthApiRequest request = new AuthApiRequest();
        request.setEmail("jan.kowalski@example.com");
        request.setPassword("Password1!");

        when(userManager.findByEmail("jan.kowalski@example.com")).thenReturn(Optional.empty());

        // when
        Executable executable = () -> sut.login(request);

        // then
        assertThrows(UserLoginException.class, executable);
        verify(userManager).findByEmail("jan.kowalski@example.com");
        verifyNoMoreInteractions(userManager);
        verifyNoInteractions(passwordHash, jwtCreator);
    }

    @Test
    void login_passwordNotCorrect() {
        // given
        AuthApiRequest request = new AuthApiRequest();
        request.setEmail("jan.kowalski@example.com");
        request.setPassword("Password1!");

        User user = new User();
        user.setPassword("35b2a70aebb9f753b28ac36c285581ea6c841f036163660fe17c89c981400acc0927f3b655b9");
        user.setSalt("b608bd32994b3d44ec0d7172ded2c8cb");
        user.setUuid(UUID.fromString("30d46e88-157b-422a-b6a8-9431e1231c15"));

        when(userManager.findByEmail("jan.kowalski@example.com")).thenReturn(Optional.of(user));
        when(passwordHash.calculateHash("Password1!", "b608bd32994b3d44ec0d7172ded2c8cb"))
                .thenReturn("c71ddb1a1a14d70aa09d46666fc9554b29c1dcfe5aa8191c277550accfd18e215cd920dd9621");

        // when
        Executable executable = () -> sut.login(request);

        // then
        assertThrows(UserLoginException.class, executable);
        verify(userManager).findByEmail("jan.kowalski@example.com");
        verify(passwordHash).calculateHash("Password1!", "b608bd32994b3d44ec0d7172ded2c8cb");
        verifyNoMoreInteractions(userManager, passwordHash);
        verifyNoInteractions(jwtCreator);
    }

    @Test
    void login() {
        // given
        AuthApiRequest request = new AuthApiRequest();
        request.setEmail(" Jan.Kowalski@example.com ");
        request.setPassword("Password1!");

        User user = new User();
        user.setPassword("35b2a70aebb9f753b28ac36c285581ea6c841f036163660fe17c89c981400acc0927f3b655b9");
        user.setSalt("b608bd32994b3d44ec0d7172ded2c8cb");
        user.setUuid(UUID.fromString("30d46e88-157b-422a-b6a8-9431e1231c15"));

        when(userManager.findByEmail("jan.kowalski@example.com")).thenReturn(Optional.of(user));
        when(passwordHash.calculateHash("Password1!", "b608bd32994b3d44ec0d7172ded2c8cb"))
                .thenReturn("35b2a70aebb9f753b28ac36c285581ea6c841f036163660fe17c89c981400acc0927f3b655b9");
        when(jwtCreator.createToken("30d46e88-157b-422a-b6a8-9431e1231c15"))
                .thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5MmRjY2ZkOC0zNjMxLTRkNTItOGM1NC1iNWM3NGE0ZTFmM2IiLCJleHAiOjE3MzYwNjA4ODB9.SykcNHF0_Fqg-le1-mve76cW00cjNKl_Y3wZWIEreGwnXqjGzqyVgi5mbqGaAou3JGqVhgEmQIo3xjmjxWYpoA");

        // when
        String actual = sut.login(request);

        // then
        assertEquals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5MmRjY2ZkOC0zNjMxLTRkNTItOGM1NC1iNWM3NGE0ZTFmM2IiLCJleHAiOjE3MzYwNjA4ODB9.SykcNHF0_Fqg-le1-mve76cW00cjNKl_Y3wZWIEreGwnXqjGzqyVgi5mbqGaAou3JGqVhgEmQIo3xjmjxWYpoA", actual);
        verify(userManager).findByEmail("jan.kowalski@example.com");
        verify(passwordHash).calculateHash("Password1!", "b608bd32994b3d44ec0d7172ded2c8cb");
        verify(jwtCreator).createToken("30d46e88-157b-422a-b6a8-9431e1231c15");
        verifyNoMoreInteractions(userManager, passwordHash, jwtCreator);
    }
}