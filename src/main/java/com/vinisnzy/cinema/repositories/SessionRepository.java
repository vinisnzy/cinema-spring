package com.vinisnzy.cinema.repositories;

import com.vinisnzy.cinema.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    List<Session> findAllByMovieId(UUID movieId);
    List<Session> findAllByRoom(String room);
}
