package com.sis5.SIS5.service;

import com.sis5.SIS5.model.dto.request.StudentRequestDTO;
import com.sis5.SIS5.model.dto.response.StudentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

    StudentResponseDTO createStudent(StudentRequestDTO requestDTO);

    StudentResponseDTO getStudentById(Long id);

    Page<StudentResponseDTO> getAllStudents(Pageable pageable);

    StudentResponseDTO updateStudent(Long id, StudentRequestDTO requestDTO);

    void deleteStudent(Long id);

    Page<StudentResponseDTO> getStudentsByCourse(String courseName, Pageable pageable);

    StudentResponseDTO getStudentByEmail(String email);
}