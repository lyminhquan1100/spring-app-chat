package spring.boot.module.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.core.api.CoreDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO extends CoreDTO {
    private Long id;

    private String username;

    private String password;

    private String email;

    private String fullName;

    private String avatar;

    private Long role;
}
