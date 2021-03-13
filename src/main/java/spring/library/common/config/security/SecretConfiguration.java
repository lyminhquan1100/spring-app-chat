package spring.library.common.config.security;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecretConfiguration {
  @Autowired
  private PropertiesConfiguration propertiesConfiguration;

  @Bean
  public SecretKey secretKey(){
    return Keys.hmacShaKeyFor(propertiesConfiguration.getSecretKey().getBytes());
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder(10);
  }
}
