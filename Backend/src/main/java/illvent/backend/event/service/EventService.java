package illvent.backend.event.service;

import illvent.backend.event.domain.Event;
import illvent.backend.event.domain.EventRepository;
import illvent.backend.event.dto.EventRegisterRequestDTO;
import illvent.backend.event.dto.EventResponseDTO;
import illvent.backend.event.dto.EventUpdateRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public Optional<Event> registerEvent(EventRegisterRequestDTO eventRegisterRequestDTO) {
        Event event = Event.builder()
                .title(eventRegisterRequestDTO.getTitle())
                .location(eventRegisterRequestDTO.getLocation())
                .address(eventRegisterRequestDTO.getAddress())
                .imageUrl(eventRegisterRequestDTO.getImageUrl())
                .description(eventRegisterRequestDTO.getDescription())
                .price(eventRegisterRequestDTO.getPrice())
                .build();

        return Optional.of(eventRepository.save(event));
    }

    @Transactional
    public EventResponseDTO updateEvent(Long eventNo, EventUpdateRequestDTO eventUpdateRequestDTO) {
        Event event = eventRepository.findById(eventNo).orElseThrow(() ->
                new IllegalArgumentException("Event not found"));

        event = Event.builder()
                .no(eventNo)
                .title(eventUpdateRequestDTO.getTitle())
                .location(eventUpdateRequestDTO.getLocation())
                .address(eventUpdateRequestDTO.getAddress())
                .imageUrl(eventUpdateRequestDTO.getImageUrl())
                .description(eventUpdateRequestDTO.getDescription())
                .price(eventUpdateRequestDTO.getPrice())
                .build();

        eventRepository.save(event);

        return new EventResponseDTO(event);
    }

    @Transactional
    public void deleteEvent(Long eventNo) {
        Event event = eventRepository.findById(eventNo).orElseThrow(() ->
                new IllegalArgumentException("Event not found"));

        eventRepository.delete(event);
    }

    public EventResponseDTO getEvent(Long eventNo) {
        Event event = eventRepository.findById(eventNo).orElseThrow(() ->
                new IllegalArgumentException("Event not found"));

        return new EventResponseDTO(event);
    }

    public List<EventResponseDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(EventResponseDTO::new)
                .collect(Collectors.toList());
    }
}
