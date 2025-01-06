package com.example.taskmanager.db.manager;

import com.example.taskmanager.db.entity.Task;
import com.example.taskmanager.db.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class TaskManagerTest {

    private AutoCloseable closeable;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TaskManager sut;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    static Stream<Arguments> findByUserAndFilter_params() {
        LocalDateTime deadlineFrom = LocalDateTime.of(2001, 1, 1, 1, 1);
        LocalDateTime deadlineTo = LocalDateTime.of(2002, 2, 2, 2, 2);
        return Stream.of(
                Arguments.of(
                        null,
                        null,
                        null,
                        "SELECT t FROM Task t WHERE t.user = :user",
                        new Consumer[]{}
                ),
                Arguments.of(
                        true,
                        null,
                        null,
                        "SELECT t FROM Task t WHERE t.user = :user AND t.isCompleted = :isCompleted",
                        new Consumer[]{
                                (Consumer<Query>) query -> verify(query).setParameter("isCompleted", true)
                        }
                ),
                Arguments.of(
                        null,
                        deadlineFrom,
                        null,
                        "SELECT t FROM Task t WHERE t.user = :user AND t.deadline >= :deadlineFrom",
                        new Consumer[]{
                                (Consumer<Query>) query -> verify(query).setParameter("deadlineFrom", deadlineFrom)
                        }
                ),
                Arguments.of(
                        null,
                        null,
                        deadlineTo,
                        "SELECT t FROM Task t WHERE t.user = :user AND t.deadline <= :deadlineTo",
                        new Consumer[]{
                                (Consumer<Query>) query -> verify(query).setParameter("deadlineTo", deadlineTo)
                        }
                ),
                Arguments.of(
                        false,
                        deadlineFrom,
                        null,
                        "SELECT t FROM Task t WHERE t.user = :user AND t.isCompleted = :isCompleted AND t.deadline >= :deadlineFrom",
                        new Consumer[]{
                                (Consumer<Query>) query -> verify(query).setParameter("isCompleted", false),
                                (Consumer<Query>) query -> verify(query).setParameter("deadlineFrom", deadlineFrom)
                        }
                ),
                Arguments.of(
                        false,
                        null,
                        deadlineTo,
                        "SELECT t FROM Task t WHERE t.user = :user AND t.isCompleted = :isCompleted AND t.deadline <= :deadlineTo",
                        new Consumer[]{
                                (Consumer<Query>) query -> verify(query).setParameter("isCompleted", false),
                                (Consumer<Query>) query -> verify(query).setParameter("deadlineTo", deadlineTo)

                        }
                ),
                Arguments.of(
                        false,
                        deadlineFrom,
                        deadlineTo,
                        "SELECT t FROM Task t WHERE t.user = :user AND t.isCompleted = :isCompleted AND t.deadline >= :deadlineFrom AND t.deadline <= :deadlineTo",
                        new Consumer[]{
                                (Consumer<Query>) query -> verify(query).setParameter("isCompleted", false),
                                (Consumer<Query>) query -> verify(query).setParameter("deadlineFrom", deadlineFrom),
                                (Consumer<Query>) query -> verify(query).setParameter("deadlineTo", deadlineTo)

                        }
                )
        );
    }

    @SafeVarargs
    @ParameterizedTest
    @MethodSource("findByUserAndFilter_params")
    final void findByUserAndFilter(Boolean isCompleted, LocalDateTime deadlineFrom, LocalDateTime deadlineTo, String expectedQuery, Consumer<Query>... verifiers) {
        // given
        User user = new User();
        TypedQuery<Task> query = mock(TypedQuery.class);

        when(entityManager.createQuery(expectedQuery, Task.class)).thenReturn(query);
        when(query.setParameter("user", user)).thenReturn(query);

        // when
        sut.findByUserAndFilter(user, isCompleted, deadlineFrom, deadlineTo);

        // then
        verify(entityManager).createQuery(expectedQuery, Task.class);
        verify(query).setParameter("user", user);
        for (Consumer<Query> c : verifiers) {
            c.accept(query);
        }
        verify(query).getResultList();
        verifyNoMoreInteractions(entityManager, query);

    }
}