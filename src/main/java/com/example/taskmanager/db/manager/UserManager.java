package com.example.taskmanager.db.manager;

import com.example.taskmanager.db.entity.User;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

public class UserManager {

    @Inject
    private EntityManager entityManager;

    public Optional<User> findByEmail(String email) {
        return entityManager.createNamedQuery(User.FIND_BY_EMAIL, User.class)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<User> findByUuid(UUID uuid) {
        return entityManager.createNamedQuery(User.FIND_BY_UUID, User.class)
                .setParameter("uuid", uuid)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Transactional
    public void persist(User user) {
        entityManager.persist(user);
    }
}
