package com.sis4.SIS4.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {
    
    @NotNull(message = "Title is required")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;
    
    @NotNull(message = "Organizer email is required")
    @Email(message = "Invalid email format")
    private String organizerEmail;
    
    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private LocalDateTime eventDate;
    
    @Min(value = 0, message = "Ticket price cannot be negative")
    private BigDecimal ticketPrice;
    
    @Min(value = 0, message = "Age limit cannot be negative")
    private Integer ageLimit;
    
    @NotBlank(message = "Location is required")
    private String location;
    
    @Min(value = 1, message = "Max attendees must be at least 1")
    private Integer maxAttendees;
    
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
}