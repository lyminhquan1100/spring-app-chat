package spring.library.common.config;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import spring.library.common.config.filter.JwtUsernamePasswordAuthenticationFilter;
import spring.library.common.config.filter.TokenVerifierFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Config extends WebSecurityConfigurerAdapter {
  private final boolean isJwtToken = true;
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
        .authorizeRequests().anyRequest().permitAll();
//      for (String str : toArray(propertiesConfiguration.getListPermit())){
//        http.authorizeRequests()
//            .antMatchers(str).permitAll();
//      }
  }

  private String[] toArray(final String source) {
    return source.split(",");
  }
}

