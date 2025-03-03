package ru.romanov.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Seats;

@Repository
public interface SeatsRepository extends JpaRepository<Seats, Long> {
}
