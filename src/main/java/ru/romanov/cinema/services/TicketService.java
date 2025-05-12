package ru.romanov.cinema.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.romanov.cinema.dtos.BulkBookingRequest;
import ru.romanov.cinema.dtos.TicketDTO;
import ru.romanov.cinema.entites.Screening;
import ru.romanov.cinema.entites.Seat;
import ru.romanov.cinema.entites.Ticket;
import ru.romanov.cinema.entites.User;
import ru.romanov.cinema.repositories.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ScreeningService screeningService;
    private final SeatService seatService;
    private final UserService userService;

    public List<Ticket> getAllUserTickets(Long userId) {
        log.info("Get all tickets for user {}", userId);
        return ticketRepository.findByUserId(userId);
    }

    @Transactional
    public List<Ticket> bookMultiple(BulkBookingRequest request) {
        log.info("Starting bulk booking for screening {} and seats {}", request.screeningId(), request.seatIds());

        Screening screening = screeningService.getScreening(request.screeningId());
        log.debug("Retrieved screening: {}", screening.getId());

        List<Seat> seats = seatService.getSeatsByIds(request.seatIds());
        log.debug("Retrieved {} seats out of requested {}", seats.size(), request.seatIds().size());

        User user = request.userId() != null ?
                userService.getById(request.userId()) :
                null;
        if (user != null) {
            log.debug("Booking for user: {}", user.getId());
        } else {
            log.debug("Booking without registered user");
        }

        if (seats.size() != request.seatIds().size()) {
            log.error("Some seats not found. Requested: {}, found: {}", request.seatIds().size(), seats.size());
            throw new IllegalArgumentException("Некоторые места не найдены");
        }

        seats.forEach(seat -> {
            if (!seat.getHall().getId().equals(screening.getHall().getId())) {
                log.error("Seat {} doesn't belong to screening hall {}", seat.getId(), screening.getHall().getId());
                throw new IllegalArgumentException("Место не принадлежит залу");
            }
        });

        List<Long> bookedSeats = ticketRepository.findBookedSeats(
                request.screeningId(),
                request.seatIds()
        );
        if (!bookedSeats.isEmpty()) {
            log.error("Seats already booked: {}", bookedSeats);
            throw new IllegalStateException("Места уже заняты: " + bookedSeats);
        }

        log.info("Creating tickets for {} seats", seats.size());
        List<Ticket> createdTickets = seats.stream()
                .map(seat -> {
                    log.debug("Creating ticket for seat {}", seat.getId());
                    return Ticket.builder()
                            .email(request.email())
                            .purchaseAt(LocalDateTime.now())
                            .status("BOOKED")
                            .screening(screening)
                            .seat(seat)
                            .price(screening.getPrice())
                            .user(user)
                            .build();
                })
                .map(ticketRepository::save)
                .toList();

        log.info("Successfully created {} tickets for screening {}", createdTickets.size(), screening.getId());
        return createdTickets;
    }

    @Transactional
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
                .email(ticketDTO.email())
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
