package ru.romanov.cinema.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanov.cinema.entites.Movie;
import ru.romanov.cinema.services.MovieService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/add-movie")
    public ResponseEntity<Movie> addMovie(Movie movie) {
        return ResponseEntity.ok(movieService.addMovie(movie));
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> getMovieById(Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping("/allTitlesOfMovies")
    public ResponseEntity<List<String>> getAllTitlesOfMovies() {
        return ResponseEntity.ok(movieService.getAllTitles());
    }


}
