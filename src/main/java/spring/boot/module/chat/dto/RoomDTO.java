package spring.boot.module.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.core.dto.BaseDTO;
import spring.boot.module.auth.dto.AccountDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO extends BaseDTO {
    private Long id;

    private Boolean active;

    private List<AccountDTO> connectedUsers;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long idNewUser;
}
