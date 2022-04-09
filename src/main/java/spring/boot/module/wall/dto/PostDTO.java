package spring.boot.module.wall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.core.api.CoreDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO extends CoreDTO {
    private Long id;

    private String content;

    private String imgPath;

    private String avatar;

    private String author;

    private Long numberLike;

    private Long numberComment;

    private Boolean isLike;

    private Long likeId;
}
