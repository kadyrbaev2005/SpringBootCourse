package com.sis4.SIS4.repository;

import com.sis4.SIS4.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByOrganizerEmail(String organizerEmail);
    
    List<Event> findByEventDateBetween(LocalDateTime start, LocalDateTime end);
    
    boolean existsByTitleAndEventDate(String title, LocalDateTime eventDate);
}