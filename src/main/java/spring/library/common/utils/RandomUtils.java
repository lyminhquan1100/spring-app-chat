package spring.library.common.utils;

import java.util.UUID;

public class RandomUtils {
  public static String getRandomId() {
    return UUID.randomUUID().toString().replace('-', '_');
  }
}
