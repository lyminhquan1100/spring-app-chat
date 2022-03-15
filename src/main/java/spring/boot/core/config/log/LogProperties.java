package spring.boot.core.config.log;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.log")
@Getter
@Setter
@NoArgsConstructor
public class LogProperties {

  private boolean includeClientInfo = true;
  private boolean includeQueryString = true;
  private boolean includePayload = true;
  private boolean includeHeaders = true;
  private int maxPayloadLength = 100000;
}