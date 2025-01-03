package com.example.taskmanager.db.manager;

import com.example.taskmanager.db.entity.Task;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

public class TaskManager {

    @Inject
    private EntityManager entityManager;

    @Transactional
    public void persist(Task task) {
        entityManager.persist(task);
    }
}
