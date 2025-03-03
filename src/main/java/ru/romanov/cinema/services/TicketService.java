package ru.romanov.cinema.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.romanov.cinema.dtos.TicketDTO;
import ru.romanov.cinema.entites.Screening;
import ru.romanov.cinema.entites.Seat;
import ru.romanov.cinema.entites.Ticket;
import ru.romanov.cinema.repositories.TicketRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ScreeningService screeningService;
    private final SeatService seatService;

    public Ticket getTicket(Long id) {
        log.info("Get ticket with id {}", id);
        return ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket with id " + id + " not found!"));
    }

    public Ticket bookTicket(TicketDTO ticketDTO) {
        Screening screening = screeningService.getScreening(ticketDTO.screeningId());
        Seat seat = seatService.getSeat(ticketDTO.seatId());

        if (!seat.getHall().getId().equals(screening.getHall().getId())) {
            log.error("Seat {} doesn't belong to screening hall {}", ticketDTO.seatId(), ticketDTO.screeningId());
            throw new IllegalArgumentException("seat doesn't belong to screening hall");
        }

        if (ticketRepository.existsByScreeningIdAndSeatIdAndStatus(
                ticketDTO.screeningId(),
                ticketDTO.seatId(),
                "BOOKED"
        )) {
            log.error("Seat {} is already booked", ticketDTO.seatId());
            throw new IllegalStateException("Seat is already booked");
        }
        log.info("Booking ticket for screening {} and seat {}", ticketDTO.screeningId(), ticketDTO.seatId());
        Ticket ticket = Ticket.builder()
                .userEmail(ticketDTO.userEmail())
                .purchaseAt(LocalDateTime.now())
                .status("BOOKED")
                .screening(screening)
                .seat(seat)
                .price(screening.getPrice())
                .user(ticketDTO.user())
                .build();
        return ticketRepository.save(ticket);
    }
}
