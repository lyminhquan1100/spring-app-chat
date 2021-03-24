package spring.backend.library.converter;

import java.lang.reflect.ParameterizedType;
import javax.persistence.AttributeConverter;
import org.springframework.data.util.CastUtils;

public class EnumConverter<Enum extends IEnum> implements AttributeConverter<Enum, Short> {

  private final Class<Enum> enumClass;

  public EnumConverter() {
    enumClass = CastUtils
        .cast(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
  }


  @Override
  public Short convertToDatabaseColumn(Enum e) {
    if (e == null) {
      return null;
    }
    return e.value();
  }

  @Override
  public Enum convertToEntityAttribute(Short s) {
    return EnumProvider.getEnum(s,enumClass);
  }
}
