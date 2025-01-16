package com.tennis.platform.repository;

import com.tennis.platform.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStudentEmail(String email);
    List<Booking> findByCoachEmail(String email);
} 