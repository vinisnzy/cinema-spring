package com.vinisnzy.cinema.repositories;

import com.vinisnzy.cinema.models.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
    @Query(value = """
        SELECT * FROM seat
        WHERE session_id = :sessionId
        ORDER BY
          SUBSTRING(code FROM 1 FOR 1),
          CAST(SUBSTRING(code FROM 2) AS INTEGER)
        """,
            countQuery = "SELECT COUNT(*) FROM seat WHERE session_id = :sessionId",
            nativeQuery = true)
    Page<Seat> findAllBySessionId(@Param("sessionId") UUID sessionId, Pageable pageable);

    @Query(value = """
        SELECT * FROM seat
        WHERE reserve_id = :reserveId
        ORDER BY
          SUBSTRING(code FROM 1 FOR 1),
          CAST(SUBSTRING(code FROM 2) AS INTEGER)
        """,
            countQuery = "SELECT COUNT(*) FROM seat WHERE reserve_id = :reserveId",
            nativeQuery = true)
    Page<Seat> findAllByReserveId(@Param("reserveId") UUID reserveId, Pageable pageable);

    List<Seat> findAllByReserveId(UUID reserveId);

    @Query(value = """
        SELECT * FROM seat
        WHERE session_id = :sessionId AND reserved = :reserved
        ORDER BY
          SUBSTRING(code FROM 1 FOR 1),
          CAST(SUBSTRING(code FROM 2) AS INTEGER)
        """,
            countQuery = "SELECT COUNT(*) FROM seat WHERE session_id = :sessionId AND reserved = :reserved",
            nativeQuery = true)
    Page<Seat> findAllBySessionIdAndReserved(
            @Param("sessionId") UUID sessionId,
            @Param("reserved") boolean reserved,
            Pageable pageable);

    List<Seat> findByCodeInAndSessionId(List<String> codes, UUID sessionId);
}
