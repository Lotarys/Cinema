package ru.romanov.cinema.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.romanov.cinema.dtos.BulkBookingRequest;
import ru.romanov.cinema.dtos.TicketDTO;
import ru.romanov.cinema.entites.Ticket;
import ru.romanov.cinema.entites.User;
import ru.romanov.cinema.services.TicketService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = ticketService.bookTicket(ticketDTO);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/book-multiple")
    public ResponseEntity<List<Ticket>> bookMultiple(@RequestBody BulkBookingRequest request) {
        return ResponseEntity.ok(ticketService.bookMultiple(request));
    }
}
