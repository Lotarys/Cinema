package ru.romanov.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Tickets;

@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Long> {
}
