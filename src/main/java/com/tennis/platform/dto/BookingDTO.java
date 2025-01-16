package com.tennis.platform.dto;

import com.tennis.platform.model.BookingStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;


@Setter
@Getter
public class BookingDTO {
    private Long id;
    private Long studentId;
    private Long coachId;
    private String studentEmail;
    private String coachEmail;
    private LocalDate date;
    private LocalTime time;
    private Integer duration;
    private BookingStatus status;
    private LocalDateTime createdAt;

}