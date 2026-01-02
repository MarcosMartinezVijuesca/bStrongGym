package com.gym.bstrong.exception;

public class BookingNotFoundException extends Exception {
    public BookingNotFoundException(String message) {
        super(message);
    }
    public BookingNotFoundException() {
        super("Booking not found");
    }
}
