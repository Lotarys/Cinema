package ru.romanov.cinema.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.romanov.cinema.entites.Movie;
import ru.romanov.cinema.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;

    public List<String> getAllTitles() {
        return new ArrayList<>(movieRepository.findAllTitles());
    }

    @Transactional
    public Movie addMovie(Movie movie) {
        log.info("Adding movie: {}", movie);
        return movieRepository.save(movie);
    }

    public Movie getMovieByTitle(String title) {
        log.info("Getting movie by title: {}", title);
        return movieRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Movie with title " + title + " not found"));
    }

    public Movie getMovieById(Long id) {
        log.info("Getting movie by id: {}", id);
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id " + id + " not found"));
    }
}
