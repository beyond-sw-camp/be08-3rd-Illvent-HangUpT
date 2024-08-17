package illvent.backend.event.service;

import illvent.backend.event.domain.DateFilter;
import illvent.backend.event.domain.EventInfo;
import illvent.backend.event.domain.EventInfoRepository;
import illvent.backend.event.domain.LocalDateRange;
import illvent.backend.event.dto.EventInfoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EventInfoService {

    private final EventInfoRepository eventInfoRepository;

    public List<EventInfoResponseDTO> getAllEvents() {
        List<EventInfo> events = eventInfoRepository.findAll();
        return events.stream().map(e -> new EventInfoResponseDTO(e.getId(), e.getTitle(), e.getImgUrl(), e.getPrice(), e.getRegion(),
                e.getEventDate(), e.isOnline(), e.isOffline(), e.getViews())).toList();
    }

    public List<EventInfoResponseDTO> getEventsByFilter(DateFilter date, String region, String join, String price) {

        List<EventInfo> events = null;

        Boolean online = null;
        Boolean offline = null;

        LocalDate startDate = null;
        LocalDate endDate = null;

        // 날짜 계산
        if (date != null) {
            LocalDateRange dateRange = date.getDateRange(); // 날짜 받을때 ENUM 타입으로
            startDate = dateRange.getStartDate();
            endDate = dateRange.getEndDate();
        }


        // on off 계산
        if (join != null) {
            if (join.equals("online")) {
                online = true;
            } else if (join.equals("offline")) {
                offline = true;
            }
        }

        if (price != null) {
            if (price.equals("무료")) {
                events = eventInfoRepository.findEventInfoByConditionAndFree(startDate, endDate, online, offline, 0,region);
            } else if (price.equals("유료")) {
                events = eventInfoRepository.findEventInfoByConditionAndPaid(startDate, endDate, online, offline, 0,region);
            }
        }else { // 전체 가격
            events = eventInfoRepository.findEventInfoByConditionAndFree(startDate, endDate, online, offline, null,region);
        }
            return events.stream().map(e -> new EventInfoResponseDTO(e.getId(), e.getTitle(), e.getImgUrl(), e.getPrice(), e.getRegion(),
                    e.getEventDate(), e.isOnline(), e.isOffline(), e.getViews())).toList();

        }
}