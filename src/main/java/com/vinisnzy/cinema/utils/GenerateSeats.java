package com.vinisnzy.cinema.utils;

import com.vinisnzy.cinema.models.seat.Seat;
import com.vinisnzy.cinema.models.session.Session;
import com.vinisnzy.cinema.repositories.SeatRepository;
import com.vinisnzy.cinema.services.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenerateSeats {

    private final SeatRepository repository;

    public List<Seat> createSeatsForSession(Session session, int totalCapacity) {
        List<Seat> seats = new ArrayList<>();

        int seatsPerRow = 20;
        int numRows = (int) Math.ceil((double) totalCapacity / seatsPerRow);

        int count = 0;
        for (int row = 0; row < numRows; row++) {
            char rowLetter = (char) ('A' + row);
            for (int number = 1; number <= seatsPerRow; number++) {
                count++;
                if (count > totalCapacity) break;
                String code = rowLetter + String.valueOf(number);
                seats.add(new Seat(null, code, false, session, null));
            }
        }

        return repository.saveAll(seats);
    }
}
