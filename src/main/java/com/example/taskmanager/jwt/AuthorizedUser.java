package com.example.taskmanager.jwt;

import jakarta.enterprise.context.RequestScoped;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequestScoped
public class AuthorizedUser {

    private UUID userUuid;
}
