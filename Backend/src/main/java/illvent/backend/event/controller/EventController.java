package illvent.backend.event.controller;

import illvent.backend.event.dto.EventsResponseDto;
import illvent.backend.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="Event",description = "행사 이벤트 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/events")
public class EventController {

    private final EventService eventService;

    // 필터별
//    @Operation(summary = "조건(필터)별로 이벤트 리스트를 가져오는 API")
//    @GetMapping("")
//    public RestController<> getEventsByFilter(){
//
//    }

    @Operation(summary = "이벤트 리스트 전체 조회 API")
    @GetMapping("")
    public ResponseEntity<List<EventsResponseDto>> getAllEvents() {

        List<EventsResponseDto> result = eventService.getAllEvents();

        return ResponseEntity.ok(result);

    }



}
