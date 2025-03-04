package ru.romanov.cinema.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.romanov.cinema.entites.Seat;
import ru.romanov.cinema.repositories.SeatRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {
    private final SeatRepository seatRepository;
    private final ScreeningService screeningService;

    public Seat getSeat(Long id) {
        log.info("Getting seat with id {}", id);
        return seatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seat with id " + id + " not found"));
    }

    public List<Seat> getAvailableSeat(Long screeningId) {
        Long hallId = screeningService.getScreening(screeningId).getHall().getId();
        log.info("Getting available seat for screening {}", screeningId);
        return seatRepository.findAvailableSeats(screeningId, hallId);
    }
}
