package illvent.backend.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class EventInfoResponseDTO {
    private Long id;
    private String title;
    private String imgUrl;
    private int price;
    private String region;
    private LocalDate eventDate;
    private boolean online;
    private boolean offline;
    private int views;

}
