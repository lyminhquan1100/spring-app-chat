package spring.boot.core.config.audit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import spring.boot.core.config.userdetail.UserPrincipal;

import java.util.Optional;

class SpringSecurityAuditAwareImpl implements AuditorAware<Long> {
  @Value("${no.security}")
  private boolean isSecurity;

  @Override
  public Optional<Long> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()
        || authentication instanceof AnonymousAuthenticationToken) {
      return Optional.empty();
    }

    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

    return Optional.ofNullable(userPrincipal.getId());
  }
}