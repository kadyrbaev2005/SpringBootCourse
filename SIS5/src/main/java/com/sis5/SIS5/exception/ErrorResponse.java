package com.sis5.SIS5.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Error response structure")
public class ErrorResponse {

    @Schema(description = "Timestamp when error occurred", example = "2026-03-11T12:00:00")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "Error type", example = "Validation Failed")
    private String error;

    @Schema(description = "Error message", example = "Input validation failed")
    private String message;

    @Schema(description = "Request path", example = "/api/v1/students")
    private String path;

    @Schema(description = "Validation errors (if any)")
    private Map<String, String> validationErrors;
}