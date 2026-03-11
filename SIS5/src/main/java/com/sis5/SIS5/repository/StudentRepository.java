package com.sis5.SIS5.repository;

import com.sis5.SIS5.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    Page<Student> findByCourseName(String courseName, Pageable pageable);

    boolean existsByEmail(String email);
}