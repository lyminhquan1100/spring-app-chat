package spring.library.common.converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.data.util.CastUtils;
import spring.library.common.exception.BaseException;
import spring.library.common.utils.NumberUtil;

public class EnumProvider {

  private final static Logger logger = LoggerFactory.getLogger(EnumProvider.class);

  private final static Map<Class<?>, Map<Short, IEnum>> map = new HashMap<>();
  private final static Map<Class<?>, Map<String, IEnum>> map2 = new HashMap<>();

  static {
    try {
      final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
      provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
      final Set<BeanDefinition> classes = provider.findCandidateComponents("vn.isofh.**.enums");
      for (BeanDefinition bean : classes) {
        Class<?> clazz = Class.forName(bean.getBeanClassName());

        if (clazz.isEnum() && IEnum.class.isAssignableFrom(clazz)) {
          IEnum[] enums = (IEnum[]) clazz.getEnumConstants();
          Map<Short, IEnum> values = new HashMap<>();
          Map<String, IEnum> values2 = new HashMap<>();
          for (IEnum e : enums) {
            values.put(e.value(), e);
            values2.put(e.name(), e);
          }
          map.put(clazz, values);
          map2.put(clazz, values2);
        }
      }
    } catch (ClassNotFoundException e) {
      logger.error(e.getMessage(), e);
    }
  }

  public static Map<Class<?>, Map<Short, IEnum>> getEnums() {
    return map;
  }

  public static <E extends IEnum> E getEnum(Object value, Class<E> enumClass) {
    if (value == null || enumClass == null) {
      return null;
    }

    Map<Short, IEnum> enumMap = map.get(enumClass);
    Map<String, IEnum> enumMap2 = map2.get(enumClass);

    if (enumMap == null) {
      throw new BaseException("error convert enum");
    }

    IEnum e = null;

    if (value instanceof Short) {
      e = enumMap.get(value);
    } else {
      Short v = NumberUtil.toShort(value);
      if (v != null) {
        e = enumMap.get(v);
      } else if (value instanceof String) {
        e = enumMap2.get(value);
      }
    }

    if (e == null) {
      throw new BaseException("error convert enum");
    }

    return CastUtils.cast(e);
  }
}
