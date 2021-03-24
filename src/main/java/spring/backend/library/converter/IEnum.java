package spring.backend.library.converter;

import com.fasterxml.jackson.annotation.JsonValue;
import spring.backend.library.msg.Message;

public interface IEnum {
  @JsonValue
  Short value();

  String name();

  default String getName() {
    return Message.getMessage(this.getClass().getSimpleName() + "." + value());
  }
}
