package ru.romanov.cinema.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.romanov.cinema.entites.Hall;
import ru.romanov.cinema.entites.Seat;
import ru.romanov.cinema.repositories.HallRepository;
import ru.romanov.cinema.repositories.SeatRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HallService {
    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;

    @Cacheable(value = "hall", key = "'hall' + #id")
    public Hall getHallById(Long id){
        log.info("Find hall with id {}", id);
        return hallRepository.findById(id)
                .orElseThrow(()->
                        new EntityNotFoundException("Hall with id " + id + " not found"));
    }

    public List<Hall> findAllHalls() {
        return new ArrayList<>(hallRepository.findAll());
    }

    public List<Seat> getAllSeatsInHall(Long hallId) {
        log.info("Getting all seats for hall {}", hallId);
        return seatRepository.findByHallId(hallId);
    }
}
