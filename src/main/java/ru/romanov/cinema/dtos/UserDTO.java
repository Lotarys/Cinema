package ru.romanov.cinema.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email,

        @Size(min = 8, message = "Password must be at least 8 characters")
        @NotBlank(message = "Password cannot be empty")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password,

        RoleName role
) {
}