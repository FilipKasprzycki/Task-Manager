package com.example.taskmanager.auth;

import com.example.taskmanager.auth.entity.AuthApiRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AutoCloseable closeable;

    @Mock
    private UserRegisterService registerService;

    @Mock
    private UserLoginService loginService;

    @InjectMocks
    private AuthService sut;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void register() {
        AuthApiRequest request = Mockito.mock(AuthApiRequest.class);

        sut.register(request);

        verify(registerService).register(request);
        verifyNoMoreInteractions(registerService);
        verifyNoInteractions(loginService);
    }

    @Test
    void login() {
        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzMGQ0NmU4OC0xNTdiLTQyMmEtYjZhOC05NDMxZTEyMzFjMTUiLCJleHAiOjE3MzYwMTc1ODB9.Rbd4pYeFgNXqTh-XrQ4s9coCde544L-vHRYU6BHSXn5EdEUy9MNfdEV2O24Os-kQzutXnSWhddnZ_dDsNJfrfw";
        AuthApiRequest request = Mockito.mock((AuthApiRequest.class));

        when(loginService.login(request)).thenReturn(jwt);

        String actual = sut.login(request);

        assertEquals(jwt, actual);
        verify(loginService).login(request);
        verifyNoMoreInteractions(loginService);
        verifyNoInteractions(registerService);
    }
}