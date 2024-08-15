package illvent.backend.post.dto;

import illvent.backend.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDTO {

    private Long no;

    private String title;

    private String content;

    private Long memberNo;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public PostResponseDTO(Post post) {
        this.no = post.getNo();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.memberNo = post.getMember().getNo();
        this.createDate = post.getCreateDate();
        this.updateDate = post.getUpdateDate();
    }
}
