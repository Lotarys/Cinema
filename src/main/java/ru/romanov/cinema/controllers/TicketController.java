package ru.romanov.cinema.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.romanov.cinema.dtos.TicketDTO;
import ru.romanov.cinema.entites.Ticket;
import ru.romanov.cinema.services.TicketService;

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
}
