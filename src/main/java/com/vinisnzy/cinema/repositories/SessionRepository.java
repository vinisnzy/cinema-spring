package com.vinisnzy.cinema.repositories;

import com.vinisnzy.cinema.models.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    List<Session> findAllByMovieId(UUID movieId);
    List<Session> findAllByRoom(String room);
}
