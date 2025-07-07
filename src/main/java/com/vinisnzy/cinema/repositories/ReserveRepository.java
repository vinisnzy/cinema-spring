package com.vinisnzy.cinema.repositories;

import com.vinisnzy.cinema.models.reserve.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReserveRepository extends JpaRepository<Reserve, UUID> {
    @Query("SELECT r FROM Reserve r LEFT JOIN FETCH r.seats WHERE r.id = :id")
    Optional<Reserve> findByIdWithSeats(@Param("id") UUID id);
    
    @Query("SELECT DISTINCT r FROM Reserve r LEFT JOIN FETCH r.seats")
    List<Reserve> findAllWithSeats();
}
