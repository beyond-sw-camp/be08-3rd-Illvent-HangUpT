package illvent.backend.event.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class EventRegisterRequestDTO {

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
    private String region;

    @NotNull
    private Long price;
}
