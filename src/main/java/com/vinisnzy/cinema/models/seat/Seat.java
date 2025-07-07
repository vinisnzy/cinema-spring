package com.vinisnzy.cinema.models.seat;

import com.vinisnzy.cinema.models.session.Session;
import com.vinisnzy.cinema.models.reserve.Reserve;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "seat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private boolean reserved;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "reserve_id")
    private Reserve reserve;
}
