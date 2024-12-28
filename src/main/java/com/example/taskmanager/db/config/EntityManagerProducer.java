package com.example.taskmanager.db.config;

import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class EntityManagerProducer {

    @Produces
    @PersistenceContext(name = "TaskManagerPersistenceUnit")
    private EntityManager entityManager;
}
