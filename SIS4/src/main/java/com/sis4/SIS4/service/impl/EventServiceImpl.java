package com.sis4.SIS4.service.impl;

import com.sis4.SIS4.exception.ResourceNotFoundException;
import com.sis4.SIS4.mapper.EventMapper;
import com.sis4.SIS4.model.dto.request.EventRequestDTO;
import com.sis4.SIS4.model.dto.response.EventResponseDTO;
import com.sis4.SIS4.model.entity.Event;
import com.sis4.SIS4.repository.EventRepository;
import com.sis4.SIS4.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public EventResponseDTO createEvent(EventRequestDTO eventRequestDTO) {
        log.info("Creating new event with title: {}", eventRequestDTO.getTitle());
        
        Event event = eventMapper.toEntity(eventRequestDTO);
        Event savedEvent = eventRepository.save(event);
        
        log.debug("Event created with ID: {}", savedEvent.getId());
        log.info("Event successfully created: {}", savedEvent.getTitle());
        
        return eventMapper.toResponseDTO(savedEvent);
    }

    @Override
    public EventResponseDTO getEventById(Long id) {
        log.debug("Fetching event with ID: {}", id);
        
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event not found with ID: {}", id);
                    return new ResourceNotFoundException("Event not found with id: " + id);
                });
        
        log.info("Event retrieved successfully: {} (ID: {})", event.getTitle(), id);
        return eventMapper.toResponseDTO(event);
    }

    @Override
    public List<EventResponseDTO> getAllEvents() {
        log.debug("Fetching all events from database");
        
        List<Event> events = eventRepository.findAll();
        
        log.info("Retrieved {} events from database", events.size());
        
        return events.stream()
                .map(eventMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventResponseDTO updateEvent(Long id, EventRequestDTO eventRequestDTO) {
        log.info("Updating event with ID: {}", id);
        
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot update - event not found with ID: {}", id);
                    return new ResourceNotFoundException("Event not found with id: " + id);
                });
        
        log.debug("Updating event fields for ID: {}", id);
        eventMapper.updateEntityFromDTO(eventRequestDTO, existingEvent);
        
        Event updatedEvent = eventRepository.save(existingEvent);
        log.info("Event updated successfully: {} (ID: {})", updatedEvent.getTitle(), id);
        
        return eventMapper.toResponseDTO(updatedEvent);
    }

    @Override
    @Transactional
    public void deleteEvent(Long id) {
        log.warn("Attempting to delete event with ID: {}", id);
        
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot delete - event not found with ID: {}", id);
                    return new ResourceNotFoundException("Event not found with id: " + id);
                });
        
        eventRepository.delete(event);
        log.info("Event deleted successfully: {} (ID: {})", event.getTitle(), id);
    }

    @Override
    public List<EventResponseDTO> getEventsByOrganizer(String email) {
        log.debug("Fetching events for organizer: {}", email);
        
        List<Event> events = eventRepository.findByOrganizerEmail(email);
        
        log.info("Found {} events for organizer: {}", events.size(), email);
        
        return events.stream()
                .map(eventMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}