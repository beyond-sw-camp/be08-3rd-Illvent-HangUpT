package illvent.backend.event.dto;

import illvent.backend.event.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponseDTO {

    private Long no;

    private String title;

    private String location;

    private String address;

    private String imageUrl;

    private String description;

    private String price;

    public EventResponseDTO(Event event){
        this.no = event.getNo();
        this.title = event.getTitle();
        this.location = event.getLocation();
        this.address = event.getAddress();
        this.imageUrl = event.getImageUrl();
        this.description = event.getDescription();
        this.price = event.getPrice();
    }
}
