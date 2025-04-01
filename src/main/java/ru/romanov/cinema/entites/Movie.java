package ru.romanov.cinema.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "age_rating", nullable = false)
    private String ageRating;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Column(name = "description", nullable = false)
    private String description;
}
