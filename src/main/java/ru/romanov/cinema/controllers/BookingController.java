package ru.romanov.cinema.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.romanov.cinema.dtos.BulkBookingRequest;
import ru.romanov.cinema.dtos.BookingDTO;
import ru.romanov.cinema.entites.Booking;
import ru.romanov.cinema.services.BookingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<Booking> bookTicket(@RequestBody BookingDTO bookingDTO) {
        Booking booking = bookingService.book(bookingDTO);
        return ResponseEntity.ok(booking);
    }

    @PostMapping("/book-multiple")
    public ResponseEntity<List<Booking>> bookMultiple(@RequestBody BulkBookingRequest request) {
        return ResponseEntity.ok(bookingService.bookMultiple(request));
    }
}
