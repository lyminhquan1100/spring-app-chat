package spring.library.common.converter;

import com.fasterxml.jackson.annotation.JsonValue;
import spring.library.common.msg.Message;

public interface IEnum {
  @JsonValue
  Short value();

  String name();

  default String getName() {
    return Message.getMessage(this.getClass().getSimpleName() + "." + value());
  }
}
