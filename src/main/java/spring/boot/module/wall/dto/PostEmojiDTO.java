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
public class PostEmojiDTO extends CoreDTO {
    private Long id;

    private Long postId;
}
