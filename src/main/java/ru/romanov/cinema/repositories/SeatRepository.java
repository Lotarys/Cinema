package ru.romanov.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Seat;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("SELECT s FROM Seat s WHERE s.hall.id = :hallId AND s.id NOT IN " +
            "(SELECT t.seat.id FROM Ticket t WHERE t.screening.id = :screeningId AND t.status = 'BOOKED')")
    List<Seat> findAvailableSeats(@Param("screeningId") Long screeningId,
                                  @Param("hallId") Long hallId);
}
