package ru.romanov.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Screenings;

@Repository
public interface ScreeningsRepository extends JpaRepository<Screenings, Long> {
}
