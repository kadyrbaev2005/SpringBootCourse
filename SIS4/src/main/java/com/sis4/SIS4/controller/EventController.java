package com.sis4.SIS4.controller;

import com.sis4.SIS4.model.dto.request.EventRequestDTO;
import com.sis4.SIS4.model.dto.response.EventResponseDTO;
import com.sis4.SIS4.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // POST /api/events - создать новое событие
    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@Valid @RequestBody EventRequestDTO eventRequestDTO) {
        log.info("REST request to create event: {}", eventRequestDTO.getTitle());
        EventResponseDTO createdEvent = eventService.createEvent(eventRequestDTO);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    // GET /api/events/{id} - получить событие по ID
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable Long id) {
        log.info("REST request to get event with id: {}", id);
        EventResponseDTO event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    // GET /api/events - получить все события
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {
        log.info("REST request to get all events");
        List<EventResponseDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    // PUT /api/events/{id} - обновить событие
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequestDTO eventRequestDTO) {
        log.info("REST request to update event with id: {}", id);
        EventResponseDTO updatedEvent = eventService.updateEvent(id, eventRequestDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    // DELETE /api/events/{id} - удалить событие
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        log.info("REST request to delete event with id: {}", id);
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/events/organizer/{email} - получить события по email организатора
    @GetMapping("/organizer/{email}")
    public ResponseEntity<List<EventResponseDTO>> getEventsByOrganizer(@PathVariable String email) {
        log.info("REST request to get events for organizer: {}", email);
        List<EventResponseDTO> events = eventService.getEventsByOrganizer(email);
        return ResponseEntity.ok(events);
    }
}