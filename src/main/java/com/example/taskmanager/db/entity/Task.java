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
public class Task extends SuperEntity {

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
