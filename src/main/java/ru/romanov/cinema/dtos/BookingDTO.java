package ru.romanov.cinema.dtos;

import ru.romanov.cinema.entites.User;

import java.math.BigDecimal;

public record BookingDTO(
        String email,
        BigDecimal price,
        Long screeningId,
        Long seatId,
        User user
) {}
