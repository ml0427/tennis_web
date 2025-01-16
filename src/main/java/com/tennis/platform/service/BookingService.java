package com.tennis.platform.service;

import com.tennis.platform.dto.BookingDTO;
import com.tennis.platform.model.Booking;
import com.tennis.platform.model.BookingStatus;
import com.tennis.platform.model.User;
import com.tennis.platform.repository.BookingRepository;
import com.tennis.platform.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingService(
            BookingRepository bookingRepository,
            UserRepository userRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("用戶不存在"));
            
        User coach = userRepository.findById(bookingDTO.getCoachId())
            .orElseThrow(() -> new RuntimeException("教練不存在"));

        Booking booking = new Booking();
        booking.setStudent(student);
        booking.setCoach(coach);
        booking.setDate(bookingDTO.getDate());
        booking.setTime(bookingDTO.getTime());
        booking.setDuration(bookingDTO.getDuration());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING);
        
        return convertToDTO(bookingRepository.save(booking));
    }

    public List<BookingDTO> getStudentBookings() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookingRepository.findByStudentEmail(email).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<BookingDTO> getCoachBookings() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookingRepository.findByCoachEmail(email).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public BookingDTO updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("預約不存在"));
            
        booking.setStatus(status);
        return convertToDTO(bookingRepository.save(booking));
    }

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        BeanUtils.copyProperties(booking, dto);
        dto.setStudentId(booking.getStudent().getId());
        dto.setCoachId(booking.getCoach().getId());
        dto.setStudentEmail(booking.getStudent().getEmail());
        dto.setCoachEmail(booking.getCoach().getEmail());
        return dto;
    }
} 