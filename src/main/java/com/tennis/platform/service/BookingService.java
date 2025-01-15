package com.tennis.platform.service;

import com.tennis.platform.dto.BookingDTO;
import com.tennis.platform.model.BookingStatus;
import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingDTO bookingDTO);
    List<BookingDTO> getStudentBookings();
    List<BookingDTO> getCoachBookings();
    BookingDTO updateBookingStatus(Long id, BookingStatus status);
} 