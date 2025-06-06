package ru.romanov.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Movies;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {
}
