package illvent.backend.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import illvent.backend.event.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponseDTO {

    @JsonProperty
    private Long no;

    @JsonProperty
    private String title;

    @JsonProperty
    private String location;

    @JsonProperty
    private String address;

    @JsonProperty
    private String imageUrl;

    @JsonProperty
    private String description;

    @JsonProperty
    private String region;

    @JsonProperty
    private Long price;

    public EventResponseDTO(Event event){
        this.no = event.getNo();
        this.title = event.getTitle();
        this.location = event.getLocation();
        this.address = event.getAddress();
        this.imageUrl = event.getImageUrl();
        this.description = event.getDescription();
        this.region = event.getRegion();
        this.price = event.getPrice();
    }
}
