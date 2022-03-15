package spring.boot.core.exception;

import lombok.Getter;
import lombok.Setter;
import spring.boot.core.dto.ResponseDTO;

@Getter
@Setter
public class BaseException extends RuntimeException {
  private ResponseDTO responseBody;

  public BaseException(int code, String message, Object data) {
    responseBody = new ResponseDTO(code,message,data);
  }

  public BaseException(String message) {
    responseBody = new ResponseDTO(1000,message,null);
  }

  public BaseException(int code,String messsage){
    responseBody = new ResponseDTO(code,messsage);
  }
}
