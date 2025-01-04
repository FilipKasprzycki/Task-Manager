package com.example.taskmanager.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
@NamedQuery(name = Task.FIND_BY_UUID_AND_USER, query = "SELECT t FROM Task t WHERE t.uuid = :uuid AND t.user = :user")
@NamedQuery(name = Task.FIND_BY_USER, query = "SELECT t FROM Task t WHERE t.user = :user")
public class Task extends SuperEntity {

    public final static String FIND_BY_UUID_AND_USER = "Task.FindByUuidAndUser";
    public final static String FIND_BY_USER = "Task.FindByUser";

    @Column(columnDefinition = "UUID")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String description;

    private LocalDateTime deadline;

    private boolean isCompleted;

    @PrePersist
    private void generateUuid() {
        this.uuid = UUID.randomUUID();
    }
}
