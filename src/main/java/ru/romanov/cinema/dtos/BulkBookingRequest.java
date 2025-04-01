package ru.romanov.cinema.dtos;

import java.util.List;

public record BulkBookingRequest(
        Long screeningId,
        List<Long> seatIds,
        String email,
        Long userId
) {}