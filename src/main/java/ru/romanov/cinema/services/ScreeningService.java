package ru.romanov.cinema.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.romanov.cinema.entites.Screening;
import ru.romanov.cinema.repositories.ScreeningRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScreeningService {

    private final ScreeningRepository screeningRepository;

    public Screening getScreening(Long id) {
        log.info("Get screening by id {}", id);
        return screeningRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Screening with id " + id + " not found"));
    }

    public List<Screening> getScreeningsByDate(LocalDate dateTime) {
        log.info("Get screenings by date {}", dateTime);
        return screeningRepository.findByStartTimeDate(dateTime);
    }

    public List<Screening> getScreeningsByMovieId(Long movieId) {
        log.info("Get screenings by movie id {}", movieId);
        return screeningRepository.findByMovieId(movieId);
    }

    @Transactional
    public Screening addScreening(Screening screening) {
        log.info("Add screening {}", screening);
        return screeningRepository.save(screening);
    }
}
