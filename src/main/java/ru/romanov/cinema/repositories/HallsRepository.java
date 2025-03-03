package ru.romanov.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Halls;

@Repository
public interface HallsRepository extends JpaRepository<Halls, Long> {
}
