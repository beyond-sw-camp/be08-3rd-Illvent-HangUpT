package illvent.backend.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CommentUpdateRequestDTO {

    @NotNull
    private String content;

    @NotNull
    private LocalDateTime updateDate;

}
