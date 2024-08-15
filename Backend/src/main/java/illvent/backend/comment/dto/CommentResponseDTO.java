package illvent.backend.comment.dto;

import illvent.backend.comment.domain.Comment;
import illvent.backend.comment.domain.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {

    private Long no;

    private String content;

    private Long level;

    private Long memberNo;

    private Long postNo;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private CommentStatus status;

    public CommentResponseDTO(Comment comment) {
        this.content = comment.getContent();
        this.level = comment.getLevel();
        this.memberNo = comment.getMember().getNo();
        this.postNo = comment.getPost().getNo();
        this.createDate = comment.getCreateDate();
        this.updateDate = comment.getUpdateDate();
        this.status = comment.getStatus();
    }
}
