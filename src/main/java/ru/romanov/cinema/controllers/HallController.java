package ru.romanov.cinema.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanov.cinema.entites.Hall;
import ru.romanov.cinema.entites.Seat;
import ru.romanov.cinema.services.HallService;
import ru.romanov.cinema.services.SeatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/halls")
public class HallController {

    private final HallService hallService;
    private final SeatService seatService;

    @GetMapping("/{id}")
    public ResponseEntity<Hall> getHall(@PathVariable Long id) {
        return ResponseEntity.ok(hallService.getHallById(id));
    }

    @GetMapping("/allHalls")
    public ResponseEntity<List<Hall>> getAllHalls() {
        return ResponseEntity.ok(hallService.findAllHalls());
    }

    @GetMapping("/{hallId}/seats")
    public ResponseEntity<List<Seat>> getAllSeatsInHall(@PathVariable Long hallId) {
        return ResponseEntity.ok(hallService.getAllSeatsInHall(hallId));
    }
}
