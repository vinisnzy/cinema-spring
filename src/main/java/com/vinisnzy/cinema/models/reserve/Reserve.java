package com.vinisnzy.cinema.models.reserve;

import com.vinisnzy.cinema.models.seat.Seat;
import com.vinisnzy.cinema.models.session.Session;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "reserve")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "reserve")
    private List<Seat> seats = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;
}
