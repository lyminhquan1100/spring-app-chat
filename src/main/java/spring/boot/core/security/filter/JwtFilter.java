package spring.boot.core.security.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.boot.core.api.ResponseDTO;
import spring.boot.core.exception.BaseException;
import spring.boot.core.security.userdetail.UserPrincipal;
import spring.boot.core.utils.ResponseUtil;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {
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
            try {
                UserDetails user = jwtProvider.getAuthentication(token);
                if (user != null) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
                }
            } catch (Exception e) {
                String msg = "";
                if (e instanceof BaseException) {
                    msg = e.getMessage();
                }

                ResponseDTO responseMsg = new ResponseDTO(401, msg);
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response, responseMsg);
                return;
            }


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
