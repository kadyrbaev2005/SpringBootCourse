package com.sis5.SIS5.controller;

import com.sis5.SIS5.model.dto.request.StudentRequestDTO;
import com.sis5.SIS5.model.dto.response.StudentResponseDTO;
import com.sis5.SIS5.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Student Management", description = "Endpoints for managing students in the system")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @Operation(
            summary = "Create a new student",
            description = "Creates a new student record with the provided information. Email must be unique."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Student created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentResponseDTO.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                        "id": 1,
                        "name": "John Doe",
                        "email": "john.doe@university.com",
                        "age": 20,
                        "courseName": "Computer Science",
                        "enrollmentDate": "2026-01-15T10:30:00",
                        "phoneNumber": "+7-777-123-4567",
                        "address": "Astana, Kazakhstan",
                        "status": "ACTIVE",
                        "createdAt": "2026-03-11T12:00:00",
                        "updatedAt": "2026-03-11T12:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input - validation error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                    {
                        "timestamp": "2026-03-11T12:00:00",
                        "status": 400,
                        "error": "Validation Failed",
                        "message": "Input validation failed",
                        "path": "/api/v1/students",
                        "validationErrors": {
                            "email": "Invalid email format",
                            "age": "Student must be at least 16 years old"
                        }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email already exists",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                    {
                        "timestamp": "2026-03-11T12:00:00",
                        "status": 409,
                        "error": "Email Already Exists",
                        "message": "Email john.doe@university.com already exists",
                        "path": "/api/v1/students"
                    }
                    """
                            )
                    )
            )
    })
    public ResponseEntity<StudentResponseDTO> createStudent(
            @Valid @RequestBody StudentRequestDTO requestDTO) {
        log.info("REST request to create student: {}", requestDTO.getEmail());
        StudentResponseDTO created = studentService.createStudent(requestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get student by ID",
            description = "Returns a single student by their unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Student found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                    {
                        "timestamp": "2026-03-11T12:00:00",
                        "status": 404,
                        "error": "Not Found",
                        "message": "Student not found with id: 999",
                        "path": "/api/v1/students/999"
                    }
                    """
                            )
                    )
            )
    })
    public ResponseEntity<StudentResponseDTO> getStudentById(
            @Parameter(description = "ID of the student", example = "1", required = true)
            @PathVariable Long id) {
        log.info("REST request to get student with id: {}", id);
        StudentResponseDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    @Operation(
            summary = "Get all students with pagination",
            description = "Returns a paginated list of all students. Supports sorting and pagination."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                        "content": [
                            {
                                "id": 1,
                                "name": "John Doe",
                                "email": "john.doe@university.com",
                                "age": 20,
                                "courseName": "Computer Science"
                            }
                        ],
                        "pageable": {
                            "pageNumber": 0,
                            "pageSize": 10,
                            "sort": {
                                "sorted": true,
                                "unsorted": false,
                                "empty": false
                            }
                        },
                        "totalPages": 5,
                        "totalElements": 50,
                        "last": false,
                        "size": 10,
                        "number": 0,
                        "sort": {
                            "sorted": true,
                            "unsorted": false,
                            "empty": false
                        },
                        "numberOfElements": 10,
                        "first": true,
                        "empty": false
                    }
                    """
                            )
                    )
            )
    })
    public ResponseEntity<Page<StudentResponseDTO>> getAllStudents(
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC)
            @Parameter(
                    description = "Pagination parameters. Default: page=0, size=10, sort=name,asc",
                    example = "page=0&size=10&sort=name,asc"
            )
            Pageable pageable) {
        log.info("REST request to get all students, page: {}, size: {}",
                pageable.getPageNumber(), pageable.getPageSize());
        Page<StudentResponseDTO> students = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing student",
            description = "Updates a student's information by their ID. All fields are optional for update."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Student updated successfully",
                    content = @Content(schema = @Schema(implementation = StudentResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input - validation error",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                    {
                        "timestamp": "2026-03-11T12:00:00",
                        "status": 404,
                        "error": "Not Found",
                        "message": "Student not found with id: 999",
                        "path": "/api/v1/students/999"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email already exists",
                    content = @Content
            )
    })
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @Parameter(description = "ID of the student to update", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDTO requestDTO) {
        log.info("REST request to update student with id: {}", id);
        StudentResponseDTO updated = studentService.updateStudent(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a student",
            description = "Deletes a student by their ID. This action cannot be undone."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Student deleted successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                    {
                        "timestamp": "2026-03-11T12:00:00",
                        "status": 404,
                        "error": "Not Found",
                        "message": "Student not found with id: 999",
                        "path": "/api/v1/students/999"
                    }
                    """
                            )
                    )
            )
    })
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "ID of the student to delete", example = "1", required = true)
            @PathVariable Long id) {
        log.info("REST request to delete student with id: {}", id);
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/course/{courseName}")
    @Operation(
            summary = "Get students by course",
            description = "Returns a paginated list of students enrolled in a specific course"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list",
                    content = @Content(schema = @Schema(implementation = Page.class))
            )
    })
    public ResponseEntity<Page<StudentResponseDTO>> getStudentsByCourse(
            @Parameter(description = "Name of the course", example = "Computer Science", required = true)
            @PathVariable String courseName,
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("REST request to get students for course: {}", courseName);
        Page<StudentResponseDTO> students = studentService.getStudentsByCourse(courseName, pageable);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/email/{email}")
    @Operation(
            summary = "Get student by email",
            description = "Returns a single student by their email address"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Student found",
                    content = @Content(schema = @Schema(implementation = StudentResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content
            )
    })
    public ResponseEntity<StudentResponseDTO> getStudentByEmail(
            @Parameter(description = "Email of the student", example = "john.doe@university.com", required = true)
            @PathVariable String email) {
        log.info("REST request to get student with email: {}", email);
        StudentResponseDTO student = studentService.getStudentByEmail(email);
        return ResponseEntity.ok(student);
    }
}