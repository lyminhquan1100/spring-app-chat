package spring.boot.module.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.core.api.CoreDTO;
import spring.boot.module.auth.dto.AccountDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LastSeenDTO extends CoreDTO {
    private Long id;

    private Long userId;

    private Long messageId;

    private Long roomId;

    private AccountDTO account;
}
