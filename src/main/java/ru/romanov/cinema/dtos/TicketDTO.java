package ru.romanov.cinema.dtos;

import ru.romanov.cinema.entites.User;

public record TicketDTO(
        String userEmail,
        double price,
        Long screeningId,
        Long seatId,
        User user
) {}
