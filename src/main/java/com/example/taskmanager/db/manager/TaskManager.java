package com.example.taskmanager.db.manager;

import com.example.taskmanager.db.entity.Task;
import com.example.taskmanager.db.entity.User;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

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

    public List<Task> findByUser(User user) {
        return entityManager.createNamedQuery(Task.FIND_BY_USER, Task.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Transactional
    public void persist(Task task) {
        entityManager.persist(task);
    }

    @Transactional
    public void delete(UUID uuid, User user) {
        findByUuidAndUser(uuid, user).ifPresent(entityManager::remove);
    }
}
