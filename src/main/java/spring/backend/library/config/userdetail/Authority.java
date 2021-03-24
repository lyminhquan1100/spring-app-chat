package spring.backend.library.config.userdetail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class Authority implements GrantedAuthority {
  private String authority;

  public Authority(String authority) {
    this.authority = "ROLE_"+authority;
  }

  @Override
  public String getAuthority() {
    return authority;
  }
}
