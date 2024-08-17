package illvent.backend.event.service;

import illvent.backend.event.domain.EventInfo;
import illvent.backend.event.domain.EventRepository;
import illvent.backend.event.dto.EventsResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;


    public List<EventsResponseDto> getAllEvents() {
        List<EventInfo> events = eventRepository.findAll();
        return events.stream().map(e-> new EventsResponseDto(e.getId(),e.getTitle(),e.getImgUrl(),e.getPrice(),e.getRegion(),
                e.getEventDate(),e.isOnline(),e.isOffline(),e.getViews())).toList();
    }
}
