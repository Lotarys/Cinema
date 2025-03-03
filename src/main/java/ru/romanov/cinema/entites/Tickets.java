package ru.romanov.cinema.entites;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "tickets")
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "purchase_at", nullable = false)
    private Timestamp purchaseAt;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "screening_id", referencedColumnName = "id", nullable = false)
    private Screenings screening;

    @ManyToOne
    @JoinColumn(name = "seats_id", referencedColumnName = "id", nullable = false)
    private Seats seats;

}
