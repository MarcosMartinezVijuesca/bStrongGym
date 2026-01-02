package com.gym.bstrong.controller;

import com.gym.bstrong.dto.BookingInDto;
import com.gym.bstrong.dto.BookingOutDto;
import com.gym.bstrong.exception.ActivityNotFoundException;
import com.gym.bstrong.exception.BookingNotFoundException;
import com.gym.bstrong.exception.MemberNotFoundException;
import com.gym.bstrong.service.BookingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    private final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @GetMapping
    public ResponseEntity<List<BookingOutDto>> getAllBookings(
            @RequestParam(required = false) Boolean attended,
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) Long activityId) {

        logger.info("GET /bookings params: attended={}, memberId={}, activityId={}", attended, memberId, activityId);
        return ResponseEntity.ok(bookingService.findAll(attended, memberId, activityId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingOutDto> getBooking(@PathVariable long id) throws BookingNotFoundException {
        logger.info("GET /bookings/{}", id);
        return ResponseEntity.ok(bookingService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookingOutDto> addBooking(@Valid @RequestBody BookingInDto bookingInDto)
            throws MemberNotFoundException, ActivityNotFoundException {
        logger.info("POST /bookings");
        return new ResponseEntity<>(bookingService.addBooking(bookingInDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingOutDto> modifyBooking(@PathVariable long id, @Valid @RequestBody BookingInDto bookingInDto)
            throws BookingNotFoundException, MemberNotFoundException, ActivityNotFoundException {
        logger.info("PUT /bookings/{}", id);
        return ResponseEntity.ok(bookingService.modifyBooking(id, bookingInDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable long id) throws BookingNotFoundException {
        logger.info("DELETE /bookings/{}", id);
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}