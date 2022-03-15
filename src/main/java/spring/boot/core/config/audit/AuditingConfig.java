package spring.boot.core.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.ZonedDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "dateTimeProvider")
class AuditingConfig {

  @Bean(name = "auditorProvider")
  public AuditorAware<Long> auditorProvider() {
    return new SpringSecurityAuditAwareImpl();
  }

  @Bean(name = "dateTimeProvider")
  public DateTimeProvider dateTimeProvider() {
    return () -> Optional.of(ZonedDateTime.now());
  }
}