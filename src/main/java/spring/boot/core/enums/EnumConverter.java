package spring.boot.core.enums;

import org.springframework.data.util.CastUtils;

import javax.persistence.AttributeConverter;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

public class EnumConverter<E extends IEnum> implements AttributeConverter<E, Short> {

  private final Class<E> enumClass;

  public EnumConverter() {
    enumClass = CastUtils
        .cast(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
  }

  @Override
  public Short convertToDatabaseColumn(E enumObj) {
    if (enumObj == null) {
      return null;
    }
    return enumObj.getValue();
  }

  @Override
  public E convertToEntityAttribute(Short dbData) {
    if (dbData != null) {
      return Arrays.stream(enumClass.getEnumConstants()).filter(e -> e.getValue() == dbData)
          .findFirst().get();
    }
    return null;
  }
}
