package ru.romanov.cinema.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "halls")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Halls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_rows")
    private int totalRows;

    @Column(name = "seats_per_row")
    private int seatsPerRow;

    @Column(name = "name")
    private String name;
}
