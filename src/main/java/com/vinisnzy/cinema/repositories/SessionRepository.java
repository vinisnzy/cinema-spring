package com.vinisnzy.cinema.repositories;

import com.vinisnzy.cinema.models.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    Page<Session> findAllByMovieId(UUID movieId, Pageable pageable);
    Page<Session> findAllByRoom(String room, Pageable pageable);
}
