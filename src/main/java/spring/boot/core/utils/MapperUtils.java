package spring.boot.core.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.util.CastUtils;

import java.util.Map;

public class MapperUtils {
  public static ObjectMapper mapper = getMapper();

  public static ObjectMapper getMapper() {
    return new ObjectMapper() {{
      disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
      enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

      registerModule(new JavaTimeModule());
    }}; // jackson's objectmapper
  }

  public static Map<String, Object> convertValue(Object fromValue) {
    if (fromValue == null) {
      return null;
    }

    return CastUtils.cast(mapper.convertValue(fromValue, Map.class));
  }

  public static <T> T convertValue(Map<String, Object> fromValue, Class<T> toValueType) {
    if (fromValue == null) {
      return null;
    }

    return mapper.convertValue(fromValue, toValueType);
  }
}
