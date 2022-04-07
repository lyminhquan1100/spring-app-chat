package spring.boot.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import spring.boot.core.msg.Message;

public interface IEnum {

  @JsonValue
  Short getValue();

  default String getName() {
    return Message.getMessage(this.getClass().getSimpleName() + "." + getValue());
  }
}
