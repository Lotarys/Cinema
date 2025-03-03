package ru.romanov.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
}
