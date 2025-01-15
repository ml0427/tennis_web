package com.tennis.platform.service.impl;

import com.tennis.platform.dto.BookingDTO;
import com.tennis.platform.model.Booking;
import com.tennis.platform.model.BookingStatus;
import com.tennis.platform.model.User;
import com.tennis.platform.repository.BookingRepository;
import com.tennis.platform.repository.UserRepository;
import com.tennis.platform.service.BookingService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    public BookingServiceImpl(
            BookingRepository bookingRepository,
            UserRepository userRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("用戶不存在"));
            
        User coach = userRepository.findById(bookingDTO.getCoachId())
            .orElseThrow(() -> new RuntimeException("教練不存在"));

        Booking booking = new Booking();
        booking.setStudent(student);
        booking.setCoach(coach);
        booking.setDate(bookingDTO.getDate());
        booking.setTime(bookingDTO.getTime());
        booking.setDuration(bookingDTO.getDuration());
        
        return convertToDTO(bookingRepository.save(booking));
    }

    @Override
    public List<BookingDTO> getStudentBookings() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookingRepository.findByStudentUsername(username).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getCoachBookings() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookingRepository.findByCoachUsername(username).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
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
        dto.setStudentName(booking.getStudent().getUsername());
        dto.setCoachName(booking.getCoach().getUsername());
        return dto;
    }
} 