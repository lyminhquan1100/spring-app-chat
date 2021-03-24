package spring.backend.library.config.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@ConfigurationProperties(value = "application.security.config")
public class PropertiesConfiguration {
  private String secretKey;
  private String tokenPrefix;
  private String tokenExpirationAfterDays;
  private String authorizationHeader;
  private String listPermit;
}
