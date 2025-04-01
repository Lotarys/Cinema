package ru.romanov.cinema.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    boolean existsByScreeningIdAndSeatIdAndStatus(Long screeningId, Long seatId, String status);

    List<Ticket> findByUserId(Long userId);

    List<Ticket> findByScreeningIdAndSeatIdInAndStatus(Long screeningId, List<Long> seatIds, String status);

    @Query("SELECT t.seat.id FROM Ticket t " +
            "WHERE t.screening.id = :screeningId AND " +
            "t.seat.id IN :seatIds AND " +
            "t.status = 'BOOKED'")
    List<Long> findBookedSeats(
            @Param("screeningId") Long screeningId,
            @Param("seatIds") List<Long> seatIds
    );

}
