package ru.romanov.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsByScreeningIdAndSeatIdAndStatus(Long screeningId, Long seatId, String status);
    List<Ticket> findByUserId(Long userId);
}
