package com.sis5.SIS5.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object containing student details")
public class StudentResponseDTO {

    @Schema(description = "Unique identifier of the student", example = "1")
    private Long id;

    @Schema(description = "Full name of the student", example = "John Doe")
    private String name;

    @Schema(description = "Email address of the student", example = "john.doe@university.com")
    private String email;

    @Schema(description = "Age of the student", example = "20")
    private Integer age;

    @Schema(description = "Course the student is enrolled in", example = "Computer Science")
    private String courseName;

    @Schema(description = "Date when student enrolled", example = "2026-01-15T10:30:00")
    private LocalDateTime enrollmentDate;

    @Schema(description = "Phone number of the student", example = "+7-777-123-4567")
    private String phoneNumber;

    @Schema(description = "Address of the student", example = "Astana, Kazakhstan")
    private String address;

    @Schema(description = "Student status", example = "ACTIVE")
    private String status;

    @Schema(description = "Date when record was created", example = "2026-03-11T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Date when record was last updated", example = "2026-03-11T12:00:00")
    private LocalDateTime updatedAt;
}