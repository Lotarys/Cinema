package ru.romanov.cinema.dtos;

public record AuthResponse(
        UserDTO user,
        String token
) {}
