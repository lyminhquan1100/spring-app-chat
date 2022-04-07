package spring.boot.module.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.core.api.CoreDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmojiDTO extends CoreDTO {
    private Long id;

    private Long roomId;

    private String fullName;

    private Long messageId;

    private Long userId;

    private Short type;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean remove = Boolean.FALSE;
}
