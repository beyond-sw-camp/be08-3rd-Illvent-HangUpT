package illvent.backend.event.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EventUpdateRequestDTO {
    @NotNull
    private String title;

    @NotNull
    private String location;

    @NotNull
    private String address;

    @NotNull
    private String imageUrl;

    @NotNull
    private String description;

    @NotNull
    private String price;
}
