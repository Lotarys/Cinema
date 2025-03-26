package ru.romanov.cinema.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.romanov.cinema.entites.Screening;
import ru.romanov.cinema.entites.Seat;
import ru.romanov.cinema.services.ScreeningService;
import ru.romanov.cinema.services.SeatService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/screenings")
@Slf4j
public class ScreeningController {

    private final ScreeningService screeningService;
    private final SeatService seatService;

    @GetMapping("/filter-by-date/{date}")
    public ResponseEntity<List<Screening>> getScreeningsByDate(@PathVariable(name = "date") LocalDate dateTime) {
        return ResponseEntity.ok(screeningService.getScreeningsByDate(dateTime));
    }

    @GetMapping("/getDatesOfScreenings")
    public ResponseEntity<List<LocalDate>> getDatesOfScreenings() {
        return ResponseEntity.ok(screeningService.getDatesOfScreenings());
    }

    @GetMapping("/filter-by-movie/{movieId}")
    public ResponseEntity<List<Screening>> getScreeningsByMovieId(@PathVariable(name = "movieId") Long movieId) {
        return ResponseEntity.ok(screeningService.getScreeningsByMovieId(movieId));
    }

    @GetMapping("/{screeningId}/available-seats")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable(name = "screeningId") Long screeningId) {
        return ResponseEntity.ok(seatService.getAvailableSeats(screeningId));
    }


    //TODO: create a dto class for request body
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-screening")
    public ResponseEntity<Screening> addScreening(@RequestBody Screening screening) {
        log.info("Received screening: {}", screening);
        return ResponseEntity.ok(screeningService.addScreening(screening));
    }

    //TODO: create a dto class for request body
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-screening/{screeningId}")
    public ResponseEntity<Screening> updateScreening(@PathVariable(name = "screeningId") Long screeningId, @RequestBody Screening screening) {
        return ResponseEntity.ok(screeningService.updateScreening(screeningId, screening));
    }

}
