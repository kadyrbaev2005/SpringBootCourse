package com.sis5.SIS5.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating/updating a student")
public class StudentRequestDTO {

    @Schema(
            description = "Full name of the student",
            example = "John Doe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Schema(
            description = "Email address of the student",
            example = "john.doe@university.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(
            description = "Age of the student",
            example = "20",
            minimum = "16",
            maximum = "100"
    )
    @Min(value = 16, message = "Student must be at least 16 years old")
    @Max(value = 100, message = "Age cannot exceed 100")
    private Integer age;

    @Schema(
            description = "Course the student is enrolled in",
            example = "Computer Science"
    )
    @Size(max = 50, message = "Course name cannot exceed 50 characters")
    private String courseName;

    @Schema(
            description = "Student enrollment date",
            example = "2026-01-15T10:30:00"
    )
    @Future(message = "Enrollment date must be in the future")
    private LocalDateTime enrollmentDate;

    @Schema(
            description = "Phone number of the student",
            example = "+7-777-123-4567"
    )
    @Pattern(regexp = "^\\+?[0-9\\-\\s]+$", message = "Invalid phone number format")
    private String phoneNumber;

    @Schema(
            description = "Address of the student",
            example = "Astana, Kazakhstan"
    )
    private String address;
}