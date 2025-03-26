package ru.romanov.cinema.dtos;

import ru.romanov.cinema.entites.User;

public record ProfileDTO(
        String email,
        String firstName,
        String lastName,
        String phone,
        RoleName role
) {
    public static ProfileDTO fromUser(User user) {
        return new ProfileDTO(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getRole().getName()
        );
    }
}
