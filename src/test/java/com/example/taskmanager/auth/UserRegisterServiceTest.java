package com.example.taskmanager.auth;

import com.example.taskmanager.auth.entity.AuthApiRequest;
import com.example.taskmanager.auth.exception.UserRegisterException;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.UserManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserRegisterServiceTest {

    private AutoCloseable closeable;

    @Mock
    private UserManager userManager;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordHash passwordHash;

    @InjectMocks
    private UserRegisterService sut;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void register_userAlreadyExists() {
        // given
        AuthApiRequest request = new AuthApiRequest();
        request.setEmail("Jan.Kowalski@example.com");
        request.setPassword("Password1!");

        when(userManager.findByEmail("jan.kowalski@example.com"))
                .thenReturn(Optional.of(new User()));

        // when
        Executable executable = () -> sut.register(request);

        // then
        assertThrows(UserRegisterException.class, executable);
        verify(userManager).findByEmail("jan.kowalski@example.com");
        verifyNoMoreInteractions(userManager);
        verifyNoInteractions(userMapper, passwordHash);
    }

    @Test
    void register() {
        // given
        AuthApiRequest request = new AuthApiRequest();
        request.setEmail("Jan.Kowalski@example.com");
        request.setPassword("Password1!");

        User user = new User();
        user.setEmail("jan.kowalski@example.com");
        user.setPassword("35b2a70aebb9f753b28ac36c285581ea6c841f036163660fe17c89c981400acc0927f3b655b9");
        user.setSalt("b608bd32994b3d44ec0d7172ded2c8cb");

        when(userManager.findByEmail("jan.kowalski@example.com"))
                .thenReturn(Optional.empty());
        when(passwordHash.getSalt())
                .thenReturn("b608bd32994b3d44ec0d7172ded2c8cb");
        when(passwordHash.calculateHash("Password1!", "b608bd32994b3d44ec0d7172ded2c8cb"))
                .thenReturn("35b2a70aebb9f753b28ac36c285581ea6c841f036163660fe17c89c981400acc0927f3b655b9");
        when(userMapper.map("jan.kowalski@example.com", "35b2a70aebb9f753b28ac36c285581ea6c841f036163660fe17c89c981400acc0927f3b655b9", "b608bd32994b3d44ec0d7172ded2c8cb"))
                .thenReturn(user);

        // when
        sut.register(request);

        // then
        verify(userManager).findByEmail("jan.kowalski@example.com");
        verify(userManager).persist(user);
        verify(passwordHash).getSalt();
        verify(passwordHash).calculateHash("Password1!", "b608bd32994b3d44ec0d7172ded2c8cb");
        verify(userMapper).map("jan.kowalski@example.com", "35b2a70aebb9f753b28ac36c285581ea6c841f036163660fe17c89c981400acc0927f3b655b9", "b608bd32994b3d44ec0d7172ded2c8cb");
        verifyNoMoreInteractions(userManager, passwordHash, userMapper);
    }
}