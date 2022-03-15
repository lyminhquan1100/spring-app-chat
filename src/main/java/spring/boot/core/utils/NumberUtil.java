package spring.boot.core.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class NumberUtil {

  public static Short toShort(Object source) {
    if (source == null) {
      return null;
    }

    if (source instanceof Number) {
      return toShort((Number) source);
    }

    if (source instanceof String) {
      String str = (String) source;
      if (NumberUtils.isCreatable(str)) {
        return NumberUtils.toShort(str);
      }
    }

    return null;
  }

}
