package spring.backend.library.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.backend.library.config.security.PropertiesConfiguration;
import spring.backend.library.config.userdetail.UserDetail;
import spring.backend.library.dto.ResponseEntity;

public class TokenVerifierFilter extends OncePerRequestFilter {
    private final PropertiesConfiguration propertiesConfiguration;
    private final SecretKey secretKey;

    public TokenVerifierFilter(PropertiesConfiguration propertiesConfiguration, SecretKey secretKey) {
        this.propertiesConfiguration = propertiesConfiguration;
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader(propertiesConfiguration.getAuthorizationHeader());

        // if string is null or empty or not prefix by configuration then return response unauthorization
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(
            propertiesConfiguration.getTokenPrefix())) {
            doFilter(httpServletRequest,httpServletResponse,filterChain);
            return;
        }

        // remove prefix on token string and get exactly token string
        String token = authorizationHeader.replace(propertiesConfiguration.getTokenPrefix(), "");

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();
            UserDetail userDetail = new UserDetail();
            userDetail.setUserId( ((Number) body.get("userId")).longValue() );
            userDetail.setUsername((String) body.get("username"));

            List<Map<String, String>> authority = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> grantetAuth = authority.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            for (SimpleGrantedAuthority simpleGrantedAuthority : grantetAuth){
                System.out.println("  + " + simpleGrantedAuthority.getAuthority().toString());
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetail,
                    null,
                    grantetAuth
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            responseUnauthorization(httpServletResponse);
            return;
//            throw new IllegalStateException(String.format("token %s cannot truest", token));
        }
        // next filter
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void responseUnauthorization(HttpServletResponse response) throws IOException {
        System.out.println("Unauthorization");
        response.setContentType("json");

        ResponseEntity<?> entityResponse = new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized", null);

        String entityresponce = new ObjectMapper().writeValueAsString(entityResponse);

        response.getWriter().write(entityresponce);
        response.getWriter().flush();
    }

}
