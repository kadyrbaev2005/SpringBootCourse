package com.sis4.SIS4.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {
    private Long id;
    private String title;
    private String organizerEmail;
    private LocalDateTime eventDate;
    private BigDecimal ticketPrice;
    private Integer ageLimit;
    private String location;
    private Integer maxAttendees;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}