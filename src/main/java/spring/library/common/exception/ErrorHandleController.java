package spring.library.common.exception;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.library.common.dto.ResponseEntity;

@ControllerAdvice
public class ErrorHandleController extends ResponseEntityExceptionHandler implements
    ErrorController {
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<?> handleBaseException(BaseException ex) {
    ResponseEntity<?> responseBody = ex.getResponseBody();

    return responseBody;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception ex) {
    ResponseEntity<?> responseBody = new ResponseEntity<>();
    responseBody.setCode(400);
    responseBody.setMessage(ex.getMessage());

    return responseBody;
  }

  @RequestMapping("/error")
  public ResponseEntity<?> error(HttpServletRequest httpServletRequest){
    System.out.println("ERROR");
    int status = Integer.valueOf(httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());

    return new ResponseEntity<>(httpServletRequest.getAttribute(RequestDispatcher.ERROR_MESSAGE));
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
