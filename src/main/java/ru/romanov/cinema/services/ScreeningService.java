package ru.romanov.cinema.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.romanov.cinema.entites.Screening;
import ru.romanov.cinema.exceptions.ConflictException;
import ru.romanov.cinema.repositories.ScreeningRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScreeningService {

    private final ScreeningRepository screeningRepository;


    @Cacheable(value = "screenings", key = "'screening_' + #id")
    public Screening getScreening(Long id) {
        log.info("Get screening by id {}", id);
        return screeningRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Screening with id " + id + " not found"));
    }


    public List<LocalDate> getDatesOfScreenings() {
        return screeningRepository.findDatesOfScreenings().stream().map(Date::toLocalDate).toList();
    }


    @Cacheable(value = "screeningsByDate", key = "'date_' + #dateTime")
    public List<Screening> getScreeningsByDate(LocalDate dateTime) {
        log.info("Get screenings by date {}", dateTime);
        return screeningRepository.findByStartTimeDate(dateTime);
    }

    @Cacheable(value = "screeningsByMovieId", key = "'movieId_' + #movieId")
    public List<Screening> getScreeningsByMovieId(Long movieId) {
        log.info("Get screenings by movie id {}", movieId);
        return screeningRepository.findByMovieId(movieId);
    }

    @Transactional
    @CacheEvict(value = {"screeningsByDate", "screeningsByMovieId", "screenings"}, allEntries = true)
    public Screening addScreening(Screening screening) {
        validateScreeningTime(screening);
        log.info("Add screening {}", screening);
        return screeningRepository.save(screening);
    }

    @Transactional
    @CacheEvict(value = {"screeningsByDate", "screeningsByMovieId", "screenings"}, allEntries = true)
    public Screening updateScreening(Long screeningId, Screening screening) {
        log.info("Update screening {}", screening);
        if (!screeningRepository.existsById(screeningId)) {
            throw new EntityNotFoundException("Screening with id " + screeningId + " not found");
        }
        Screening updatedScreening = new Screening(
                screeningId,
                screening.getStartTime(),
                screening.getPrice(),
                screening.getMovie(),
                screening.getHall()
        );
        return screeningRepository.save(updatedScreening);
    }

    private void validateScreeningTime(Screening screening) {
        boolean exists = screeningRepository.existsByHallAndStartTimeBetween(
                screening.getHall(),
                screening.getStartTime().minusHours(2),
                screening.getStartTime().plusHours(2)
        );
        if (exists) {
            throw new ConflictException("Сеанс пересекается с существующим");
        }
    }
}
