package com.example.taskmanager.task;

import com.example.taskmanager.db.entity.Task;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.db.manager.TaskManager;
import com.example.taskmanager.task.entity.TaskCreateApiRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

class TaskCreateServiceTest {

    private AutoCloseable closeable;

    @Mock
    private TaskManager taskManager;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskCreateService sut;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void create() {
        // given
        User user = new User();
        user.setUuid(UUID.fromString("30d46e88-157b-422a-b6a8-9431e1231c15"));

        TaskCreateApiRequest request = new TaskCreateApiRequest();
        request.setTitle("Write tests");
        request.setDescription("Use jUnit and mockito");
        request.setDeadline(LocalDateTime.of(2001, 1, 1, 1, 1));
        request.setIsCompleted(false);

        Task task = new Task();
        task.setUser(user);
        task.setTitle("Write tests");
        task.setDescription("Use jUnit and mockito");
        request.setDeadline(LocalDateTime.of(2001, 1, 1, 1, 1));
        task.setCompleted(false);

        when(taskMapper.map(request, user)).thenReturn(task);

        // when
        sut.create(user, request);

        // then
        verify(taskMapper).map(request, user);
        verify(taskManager).persist(task);
        verifyNoMoreInteractions(taskManager, taskManager);
    }
}