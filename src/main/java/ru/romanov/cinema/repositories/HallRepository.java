package ru.romanov.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.cinema.entites.Hall;

import java.util.List;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
}
