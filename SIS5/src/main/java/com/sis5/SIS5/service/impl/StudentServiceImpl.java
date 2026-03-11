package com.sis5.SIS5.service.impl;

import com.sis5.SIS5.exception.EmailAlreadyExistsException;
import com.sis5.SIS5.exception.ResourceNotFoundException;
import com.sis5.SIS5.mapper.StudentMapper;
import com.sis5.SIS5.model.dto.request.StudentRequestDTO;
import com.sis5.SIS5.model.dto.response.StudentResponseDTO;
import com.sis5.SIS5.model.entity.Student;
import com.sis5.SIS5.repository.StudentRepository;
import com.sis5.SIS5.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
        log.info("Creating new student with email: {}", requestDTO.getEmail());

        if (studentRepository.existsByEmail(requestDTO.getEmail())) {
            log.error("Email already exists: {}", requestDTO.getEmail());
            throw new EmailAlreadyExistsException("Email " + requestDTO.getEmail() + " already exists");
        }

        Student student = studentMapper.toEntity(requestDTO);
        Student savedStudent = studentRepository.save(student);

        log.debug("Student created with ID: {}", savedStudent.getId());
        log.info("Student successfully created: {}", savedStudent.getEmail());

        return studentMapper.toResponseDTO(savedStudent);
    }

    @Override
    public StudentResponseDTO getStudentById(Long id) {
        log.debug("Fetching student with ID: {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student not found with ID: {}", id);
                    return new ResourceNotFoundException("Student not found with id: " + id);
                });

        log.info("Student retrieved successfully: {} (ID: {})", student.getEmail(), id);
        return studentMapper.toResponseDTO(student);
    }

    @Override
    public Page<StudentResponseDTO> getAllStudents(Pageable pageable) {
        log.debug("Fetching all students, page: {}, size: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Student> students = studentRepository.findAll(pageable);

        log.info("Retrieved {} students (page {})", students.getNumberOfElements(),
                pageable.getPageNumber());

        return students.map(studentMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO requestDTO) {
        log.info("Updating student with ID: {}", id);

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot update - student not found with ID: {}", id);
                    return new ResourceNotFoundException("Student not found with id: " + id);
                });

        if (!existingStudent.getEmail().equals(requestDTO.getEmail()) &&
                studentRepository.existsByEmail(requestDTO.getEmail())) {
            log.error("Email already exists: {}", requestDTO.getEmail());
            throw new EmailAlreadyExistsException("Email " + requestDTO.getEmail() + " already exists");
        }

        log.debug("Updating student fields for ID: {}", id);
        studentMapper.updateEntityFromDTO(requestDTO, existingStudent);

        Student updatedStudent = studentRepository.save(existingStudent);
        log.info("Student updated successfully: {} (ID: {})", updatedStudent.getEmail(), id);

        return studentMapper.toResponseDTO(updatedStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        log.warn("Attempting to delete student with ID: {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot delete - student not found with ID: {}", id);
                    return new ResourceNotFoundException("Student not found with id: " + id);
                });

        studentRepository.delete(student);
        log.info("Student deleted successfully: {} (ID: {})", student.getEmail(), id);
    }

    @Override
    public Page<StudentResponseDTO> getStudentsByCourse(String courseName, Pageable pageable) {
        log.debug("Fetching students for course: {}", courseName);

        Page<Student> students = studentRepository.findByCourseName(courseName, pageable);

        log.info("Found {} students for course: {}", students.getNumberOfElements(), courseName);

        return students.map(studentMapper::toResponseDTO);
    }

    @Override
    public StudentResponseDTO getStudentByEmail(String email) {
        log.debug("Fetching student with email: {}", email);

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Student not found with email: {}", email);
                    return new ResourceNotFoundException("Student not found with email: " + email);
                });

        log.info("Student retrieved successfully: {} (ID: {})", student.getEmail(), student.getId());
        return studentMapper.toResponseDTO(student);
    }
}