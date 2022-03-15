package spring.boot.core.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.boot.core.dto.ResponseDTO;
import spring.boot.core.msg.Message;

@RestControllerAdvice
public class ErrorHandleController extends ResponseEntityExceptionHandler{

  @ExceptionHandler(Exception.class)
  public ResponseDTO handleBaseException(Exception ex) {
    ResponseDTO responseBody = new ResponseDTO(500,ex.getMessage());

    return responseBody;
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseDTO handleAccessDeniedException(AccessDeniedException ex) {
    ResponseDTO responseBody = new ResponseDTO(401,
        Message.getMessage("AccessDeniedHandle.handleAccessDeniedException"));

    return responseBody;
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseDTO handleInvalidToken(InvalidTokenException ex) {
    ResponseDTO responseBody = new ResponseDTO(402,
        Message.getMessage("AuthenticationEntryPointImpl.invalidToken"));

    return responseBody;
  }

  @ExceptionHandler(BaseException.class)
  public ResponseDTO handleException(BaseException ex) {
    ResponseDTO responseBody = new ResponseDTO();
    responseBody.setCode(ex.getResponseBody().getCode());
    responseBody.setMessage(ex.getResponseBody().getMessage());

    return responseBody;
  }
}
