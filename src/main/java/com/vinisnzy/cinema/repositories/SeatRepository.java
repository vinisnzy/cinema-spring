package com.vinisnzy.cinema.repositories;

import com.vinisnzy.cinema.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
    List<Seat> findAllBySessionId(UUID sessionId);
    List<Seat> findAllByReserveId(UUID reserveId);
    List<Seat> findAllBySessionIdAndReserved(UUID sessionId, boolean reserved);
    List<Seat> findByCodeInAndSessionId(List<String> codes, UUID sessionId);
}
