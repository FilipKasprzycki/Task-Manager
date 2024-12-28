package com.example.taskmanager.db.manager;

import com.example.taskmanager.db.entity.User;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;

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

    @Transactional
    public void persist(User user) {
        entityManager.persist(user);
    }
}
