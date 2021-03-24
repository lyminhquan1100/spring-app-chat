package spring.backend.library.config.filter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import spring.backend.library.config.dto.UsernameAndPasswordDto;
import spring.backend.library.config.security.PropertiesConfiguration;
import spring.backend.library.config.security.TokenProvider;
import spring.backend.library.config.userdetail.UserDetail;
import spring.backend.library.dto.ResponseEntity;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final PropertiesConfiguration propertiesConfiguration;
    private final SecretKey secretKey;
    @Autowired
    private TokenProvider tokenProvider;

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, PropertiesConfiguration propertiesConfiguration, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.propertiesConfiguration = propertiesConfiguration;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordDto accountDAO = new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordDto.class);

            Authentication authentication;

            if (accountDAO == null) {
                authentication = new UsernamePasswordAuthenticationToken("","");
            }
            else
                authentication = new UsernamePasswordAuthenticationToken(
                        accountDAO.getUsername(),
                        accountDAO.getPassword()
                );

            Authentication authenticate = authenticationManager.authenticate(authentication);

            return authenticate;
        } catch (JsonParseException e){
            System.out.println("error Json Parse");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetail userDetail = (UserDetail) authResult.getPrincipal();

        String token = tokenProvider.generateToken(userDetail);

        response.addHeader(propertiesConfiguration.getAuthorizationHeader(),token);

        // return response successful login
        response.setContentType("json");
        ResponseEntity<?> entityResponse = new ResponseEntity<>(HttpStatus.OK.value(), "Login Successful. Has a Token response for you !!!", token);

        String entityResponce = new ObjectMapper().writeValueAsString(entityResponse);

        response.getWriter().write(entityResponce);
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // return response error

        response.setContentType("json;charset=ISO-8859-1");

        ResponseEntity<?> entityResponse = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE.value(), "username or password is not correct", null);

        String entityresponce = new ObjectMapper().writeValueAsString(entityResponse);

        response.getWriter().write(entityresponce);
        response.getWriter().flush();
    }
}
