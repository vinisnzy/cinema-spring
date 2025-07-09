package com.vinisnzy.cinema.exceptions;

public class SeatAlreadyReservedException extends RuntimeException{
    public SeatAlreadyReservedException(String message) {
        super(message);
    }
}
