package spring.boot.module.chat.dto;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.core.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO extends BaseDTO {
    private Long id;

    private Long roomId;

    private Long toUser;

    private String content;

    private String fullName;

    private String avatar;

    public boolean isPublic() {
        return this.toUser == null;
    }
}
