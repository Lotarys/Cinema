package ru.romanov.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Hall;
import ru.romanov.cinema.entites.Screening;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT s FROM Screening s WHERE CAST(s.startTime AS date) = :date")
    List<Screening> findByStartTimeDate(@Param("date") LocalDate date);

    List<Screening> findByMovieId(Long movieId);

    @Query("SELECT DISTINCT CAST(s.startTime AS date) FROM Screening s")
    List<Date> findDatesOfScreenings();

    boolean existsByHallAndStartTimeBetween(Hall hall, LocalDateTime startTime, LocalDateTime endTime);
}
