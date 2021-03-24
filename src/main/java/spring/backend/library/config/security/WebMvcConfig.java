package spring.backend.library.config.security;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import spring.backend.library.msg.Message;

@Configuration
public class WebMvcConfig {
  @Bean
  public LocaleResolver localeResolver() {
    CookieLocaleResolver lr = new CookieLocaleResolver() {
      @Override
      protected Locale determineDefaultLocale(HttpServletRequest request) {
        Locale defaultLocale = getDefaultLocale();
        if (request.getHeader("Accept-Language") != null) {
          defaultLocale = request.getLocale();
        }
        return defaultLocale;
      }
    };

    lr.setDefaultLocale(Message.getLocaleDefault());
    return lr;
  }
}
