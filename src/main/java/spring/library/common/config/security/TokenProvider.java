package spring.library.common.config.security;

import io.jsonwebtoken.Jwts;
import java.time.LocalDate;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.library.common.config.userdetail.UserDetail;

@Component
public class TokenProvider {
  @Autowired
  private SecretKey secretKey;

  public String generateToken(UserDetail userDetail){
    return Jwts.builder()
        .claim("userId", userDetail.getUserId())
        .claim("username", userDetail.getUsername())
        .claim("authorities", userDetail.getAuthorities())
        .setIssuedAt(new Date())
        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
        .signWith(secretKey)
        .compact();
  }
}
