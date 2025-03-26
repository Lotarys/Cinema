package ru.romanov.cinema.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanov.cinema.entites.Hall;
import ru.romanov.cinema.services.HallService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/halls")
public class HallController {

    private final HallService hallService;

    @GetMapping("/{id}")
    public ResponseEntity<Hall> getHall(@PathVariable Long id) {
        return ResponseEntity.ok(hallService.getHallById(id));
    }

    @GetMapping("/allHalls")
    public ResponseEntity<List<Hall>> getAllHalls() {
        return ResponseEntity.ok(hallService.findAllHalls());
    }
}
