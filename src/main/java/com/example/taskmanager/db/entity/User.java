package com.example.taskmanager.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
public class User extends SuperEntity {

    public final static String FIND_BY_EMAIL = "User.FindByEmail";

    private UUID uuid;

    private String email;

    private String password;

    private String salt;

    @PrePersist
    void generateUuid() {
        this.uuid = UUID.randomUUID();
    }
}
