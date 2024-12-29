package com.example.taskmanager.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthApiRequest {

    @Email(message = "email not valid")
    @NotNull(message = "email is required")
    private String email;

    @NotNull(message = "password is required")
    @Size(min = 8, message = "password is too short - min 8 chars")
    @Size(max = 1_000, message = "password is too long")
    @ToString.Exclude
    private String password;
}
