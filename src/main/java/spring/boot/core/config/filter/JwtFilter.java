package spring.boot.core.config.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class  JwtFilter extends OncePerRequestFilter {
  private final SecretKey secretKey;

  public JwtFilter(SecretKey secretKey) {
    this.secretKey = secretKey;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    JwtProvider jwtProvider = new JwtProvider(secretKey);
    String token = getJwtFromRequest(request);
    if (token != null) {
      SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(token));
    }
    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    // Kiểm tra xem header Authorization có chứa thông tin jwt không
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
