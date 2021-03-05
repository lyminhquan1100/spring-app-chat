package spring.library.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEntity<T> {
  private Integer code;
  private String message;
  private Object data;
  private Integer numberOfElements;
  private Long totalElements;

  public ResponseEntity(){
    this.numberOfElements = 1;
    totalElements = (long) numberOfElements;
  }
  public ResponseEntity(T data){
    this();
    this.data = data;
  }
  public ResponseEntity(Integer code, String message){
    this();
    this.code = code;
    this.message = message;
  }

  public ResponseEntity(Integer code, String message, T data) {
    this();
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public ResponseEntity(Integer code, String message, T data,Integer numberOfElements,Long totalElements) {
    this.code = code;
    this.message = message;
    this.data = data;
    this.numberOfElements = numberOfElements;
    this.totalElements = totalElements;
  }
}
