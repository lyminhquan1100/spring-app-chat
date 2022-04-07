package spring.boot.module.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.core.api.CoreDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO extends CoreDTO {
    private Long id;

    private Long fromId;

    private Long roomId;

    private String content;

    /**
     * 1: message
     * 2: image
     */
    private Short type;

    private String fullName;

    private String avatar;

    private List<LastSeenDTO> listLastSeen;

    private List<EmojiDTO> listEmoji;
}
