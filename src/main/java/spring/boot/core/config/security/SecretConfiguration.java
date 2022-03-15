package spring.boot.core.config.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class SecretConfiguration {
  @Autowired
  private PropertiesConfiguration propertiesConfiguration;

  @Bean
  public SecretKey secretKey(){
    return Keys.hmacShaKeyFor(propertiesConfiguration.getSecretKey().getBytes());
  }
}
