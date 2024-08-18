package illvent.backend.event.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private int views;

    @NotNull
    private int likes;

    @NotNull
    private boolean online;

    @NotNull
    private boolean offline;
}
