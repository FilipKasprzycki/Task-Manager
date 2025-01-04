package com.example.taskmanager.db.manager;

import com.example.taskmanager.db.entity.Task;
import com.example.taskmanager.db.entity.User;
import com.example.taskmanager.task.entity.TaskUpdateApiRequest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskManager {

    @Inject
    private EntityManager entityManager;

    public Optional<Task> findByUuidAndUser(UUID uuid, User user) {
        return entityManager.createNamedQuery(Task.FIND_BY_UUID_AND_USER, Task.class)
                .setParameter("uuid", uuid)
                .setParameter("user", user)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    public List<Task> findByUserAndFilter(User user, Boolean isCompleted, LocalDateTime deadlineFrom, LocalDateTime deadlineTo) {
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM Task t WHERE t.user = :user");
        if (isCompleted != null) {
            queryBuilder.append(" AND t.isCompleted = :isCompleted");
        }
        if (deadlineFrom != null) {
            queryBuilder.append(" AND t.deadline >= :deadlineFrom");
        }
        if (deadlineTo != null) {
            queryBuilder.append(" AND t.deadline <= :deadlineTo");
        }

        TypedQuery<Task> query = entityManager.createQuery(queryBuilder.toString(), Task.class)
                .setParameter("user", user);

        if (isCompleted != null) {
            query.setParameter("isCompleted", isCompleted);
        }
        if (deadlineFrom != null) {
            query.setParameter("deadlineFrom", deadlineFrom);
        }
        if (deadlineTo != null) {
            query.setParameter("deadlineTo", deadlineTo);
        }

        return query.getResultList();
    }

    @Transactional
    public void persist(Task task) {
        entityManager.persist(task);
    }

    @Transactional
    public void delete(UUID uuid, User user) {
        findByUuidAndUser(uuid, user).ifPresent(entityManager::remove);
    }

    @Transactional
    public void update(User user, TaskUpdateApiRequest request) {
        Task task = findByUuidAndUser(request.getUuid(), user)
                .orElseThrow(() -> new NotFoundException(String.format("not found task [UUID: %s] for user [UUID: %s]", request.getUuid(), user.getUuid())));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setCompleted(request.getIsCompleted());
    }
}
