package com.example.taskmanager.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "api_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "uuid"),
                @UniqueConstraint(columnNames = "email")
        })
@NamedQuery(name = User.FIND_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email = :email")
public class User {

    public final static String FIND_BY_EMAIL = "User.FindByEmail";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;

    private String email;

    private String password;

    private String salt;

    private LocalDateTime createAt;

    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        this.uuid = UUID.randomUUID();
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
