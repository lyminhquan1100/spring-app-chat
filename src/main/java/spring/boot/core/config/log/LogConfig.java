package spring.boot.core.config.log;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@EnableConfigurationProperties(LogProperties.class)
public class LogConfig {

  @Autowired
  private LogProperties logProperties;

  @Bean
  public CommonsRequestLoggingFilter requestLoggingFilter() {
    CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
    loggingFilter.setIncludeClientInfo(logProperties.isIncludeClientInfo());
    loggingFilter.setIncludeQueryString(logProperties.isIncludeQueryString());
    loggingFilter.setIncludePayload(logProperties.isIncludePayload());
    loggingFilter.setIncludeHeaders(logProperties.isIncludeHeaders());
    loggingFilter.setMaxPayloadLength(logProperties.getMaxPayloadLength());
    return loggingFilter;
  }
}