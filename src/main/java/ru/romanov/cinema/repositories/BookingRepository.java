package ru.romanov.cinema.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByScreeningIdAndSeatIdAndStatus(Long screeningId, Long seatId, String status);

    List<Booking> findByUserId(Long userId);

    @Query("SELECT b.seat.id FROM Booking b " +
            "WHERE b.screening.id = :screeningId AND " +
            "b.seat.id IN :seatIds AND " +
            "b.status = 'BOOKED'")
    List<Long> findBookedSeats(
            @Param("screeningId") Long screeningId,
            @Param("seatIds") List<Long> seatIds
    );

}
