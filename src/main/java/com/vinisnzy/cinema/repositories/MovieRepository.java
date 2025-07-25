package com.vinisnzy.cinema.repositories;

import com.vinisnzy.cinema.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
}
