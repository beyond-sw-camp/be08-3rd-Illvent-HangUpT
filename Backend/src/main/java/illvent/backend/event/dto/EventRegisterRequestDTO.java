package illvent.backend.event.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate eventDate;

    @NotNull
    private String imageUrl;

    @NotNull
    private String description;

    @NotNull
    private String region;

    @NotNull
    private int price;

    @NotNull
    private boolean online;

    @NotNull
    private boolean offline;
}
