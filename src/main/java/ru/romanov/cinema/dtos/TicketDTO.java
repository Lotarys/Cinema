package ru.romanov.cinema.dtos;

import ru.romanov.cinema.entites.User;

import java.math.BigDecimal;

public record TicketDTO(
        String email,
        BigDecimal price,
        Long screeningId,
        Long seatId,
        User user
) {}
