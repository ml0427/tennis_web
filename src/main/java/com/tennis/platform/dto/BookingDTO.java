package com.tennis.platform.dto;

import com.tennis.platform.model.BookingStatus;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDTO {
    private Long id;
    private Long studentId;
    private Long coachId;
    private String studentName;
    private String coachName;
    private LocalDate date;
    private LocalTime time;
    private Integer duration;
    private BookingStatus status;
} 