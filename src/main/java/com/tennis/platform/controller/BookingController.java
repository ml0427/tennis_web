package com.tennis.platform.controller;

import com.tennis.platform.dto.BookingDTO;
import com.tennis.platform.model.BookingStatus;
import com.tennis.platform.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:8080")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.createBooking(bookingDTO));
    }

    @GetMapping("/student")
    public ResponseEntity<List<BookingDTO>> getStudentBookings() {
        return ResponseEntity.ok(bookingService.getStudentBookings());
    }

    @GetMapping("/coach")
    public ResponseEntity<List<BookingDTO>> getCoachBookings() {
        return ResponseEntity.ok(bookingService.getCoachBookings());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(
        @PathVariable Long id,
        @RequestParam BookingStatus status
    ) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }
} 