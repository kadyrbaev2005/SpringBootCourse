package com.example.postservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Error response object")
public class ErrorResponse {

    @Schema(description = "Timestamp of error", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Error status code", example = "404")
    private int status;

    @Schema(description = "Error message", example = "Post not found")
    private String message;

    @Schema(description = "Error details", example = "Post with id 123 not found")
    private String details;

    public ErrorResponse(int status, String message, String details) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.details = details;
    }

    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public String getDetails() { return details; }
}