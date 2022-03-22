package spring.boot.module.chat.dto;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.core.dto.BaseDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO extends BaseDTO {
    private Long id;

    private Long fromId;

    private Long roomId;

    private String content;

    private String fullName;

    private String avatar;

    private List<LastSeenDTO> listLastSeen;
}
