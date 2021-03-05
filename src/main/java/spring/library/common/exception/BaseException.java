package spring.library.common.exception;

import lombok.Getter;
import lombok.Setter;
import spring.library.common.dto.ResponseEntity;

@Getter
@Setter
public class BaseException extends RuntimeException {
  private ResponseEntity<?> responseBody;

  public BaseException(int code, String message, Object data) {
    responseBody = new ResponseEntity<>(code,message,data);
  }

  public BaseException(String message) {
    responseBody = new ResponseEntity<>(400,message,null);
  }

  public BaseException(int code,String messsage){
    responseBody = new ResponseEntity<>(400,messsage);
  }
}
