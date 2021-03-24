package spring.backend.library.config.filter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import spring.backend.library.constant.ErrorEnum;
import spring.backend.library.dto.ResponseEntity;
import spring.backend.library.msg.Message;

@Component
public class AccessDeniedHandle implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      AccessDeniedException e) throws IOException, ServletException {
    ResponseEntity<?> response = new ResponseEntity<>(ErrorEnum.UNAUTHORIZED.getCode(), Message
        .getMessage(ErrorEnum.UNAUTHORIZED.getMessage()));
  }
}
