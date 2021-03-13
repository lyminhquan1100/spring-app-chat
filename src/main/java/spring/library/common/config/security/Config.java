package spring.library.common.config.security;

import com.google.common.collect.ImmutableList;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import spring.library.common.config.filter.AccessDeniedHandle;
import spring.library.common.config.filter.AuthenticationEntryPointHandle;
import spring.library.common.config.filter.JwtUsernamePasswordAuthenticationFilter;
import spring.library.common.config.filter.TokenVerifierFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Config extends WebSecurityConfigurerAdapter {
  private final boolean isJwtToken = true;

  @Autowired
  protected AccessDeniedHandle accessDeniedHandle;

  @Autowired
  protected AuthenticationEntryPointHandle authenticationEntryPointHandle;
  @Autowired
  protected PropertiesConfiguration propertiesConfiguration;
  @Autowired
  protected SecretKey secretKey;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    if (isJwtToken) {
      http
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), propertiesConfiguration, secretKey))
          .addFilterAfter(new TokenVerifierFilter(propertiesConfiguration, secretKey), JwtUsernamePasswordAuthenticationFilter.class);
    }
    http.csrf().disable()
//        .cors().configurationSource(corsConfigurationSource())
//        .and()
//        .exceptionHandling().accessDeniedHandler(accessDeniedHandle)
//        .and()
//        .exceptionHandling().authenticationEntryPoint(authenticationEntryPointHandle)
//        .and()
        .authorizeRequests()
        .anyRequest().permitAll();
//      for (String str : toArray(propertiesConfiguration.getListPermit())){
//        http.authorizeRequests()
//            .antMatchers(str).permitAll();
//      }
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(ImmutableList.of("*"));
    configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
    // setAllowCredentials(true) is important, otherwise:
    // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
    configuration.setAllowCredentials(true);
    // setAllowedHeaders is important! Without it, OPTIONS preflight request
    // will fail with 403 Invalid CORS request
    configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  private String[] toArray(final String source) {
    return source.split(",");
  }
}

