package com.example.taskmanager.task;

import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class TaskDeleteServiceTest {

    private AutoCloseable closeable;

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private TaskDeleteService sut;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void delete() {
        User user = new User();
        user.setUuid(UUID.fromString("30d46e88-157b-422a-b6a8-9431e1231c15"));
        UUID taskUuid = UUID.fromString("672bffe4-707f-4345-8494-c960d84160fe");

        sut.delete(user, taskUuid);

        verify(taskManager).delete(taskUuid, user);
        verifyNoMoreInteractions(taskManager);
    }
}