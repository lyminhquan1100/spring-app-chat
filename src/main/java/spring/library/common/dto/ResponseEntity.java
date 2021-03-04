package spring.library.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseEntity<T> {
  private Integer code;
  private String message;
  private T data;
  private Long numberOfElements;
  private Long totalElements;

  public ResponseEntity(T data){
    this.data = data;
  }

  public ResponseEntity(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }
}
