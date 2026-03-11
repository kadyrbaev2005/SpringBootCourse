package com.sis4.SIS4.service;

import com.sis4.SIS4.model.dto.request.EventRequestDTO;
import com.sis4.SIS4.model.dto.response.EventResponseDTO;

import java.util.List;

public interface EventService {
    EventResponseDTO createEvent(EventRequestDTO eventRequestDTO);
    EventResponseDTO getEventById(Long id);
    List<EventResponseDTO> getAllEvents();
    EventResponseDTO updateEvent(Long id, EventRequestDTO eventRequestDTO);
    void deleteEvent(Long id);
    List<EventResponseDTO> getEventsByOrganizer(String email);
}