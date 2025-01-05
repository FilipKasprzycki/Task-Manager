package com.example.taskmanager.db.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "api_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "uuid"),
                @UniqueConstraint(columnNames = "email")
        })
@NamedQuery(name = User.FIND_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email = :email")
@NamedQuery(name = User.FIND_BY_UUID, query = "SELECT u FROM User u WHERE u.uuid = :uuid")
public class User extends SuperEntity {

    public final static String FIND_BY_EMAIL = "User.FindByEmail";
    public final static String FIND_BY_UUID = "User.FindByUuid";

    @Column(columnDefinition = "UUID")
    private UUID uuid;

    private String email;

    private String password;

    private String salt;

    @PrePersist
    void generateUuid() {
        this.uuid = UUID.randomUUID();
    }
}
