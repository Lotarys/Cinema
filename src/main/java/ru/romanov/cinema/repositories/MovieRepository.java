package ru.romanov.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
