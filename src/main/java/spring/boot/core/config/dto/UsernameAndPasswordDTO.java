package spring.boot.core.config.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsernameAndPasswordDTO {
  private String username;
  private String password;
}
