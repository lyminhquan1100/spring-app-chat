package spring.boot.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

  private final static Logger logger = LoggerFactory.getLogger(StringUtil.class);

  public static String deleteAccents(String text) {
    if (text == null) {
      return null;
    }

    String nfdNormalizedString = Normalizer.normalize(text, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    text = pattern.matcher(nfdNormalizedString).replaceAll("")
        .replaceAll("Đ", "D")
        .replaceAll("đ", "d");

    return text;
  }

  public static String cleanNonVisibleCharacters(String text) {
    if (text == null) {
      return null;
    }

    return text.replaceAll("\\s+", "");
  }

  public static String normalizeSpace(String text) {
    if (text == null) {
      return null;
    }

    return StringUtils.normalizeSpace(text);
  }

  public static String replaceUTF16SurrogatePairs(String str) {
    if (StringUtil.isBlank(str)) {
      return null;
    }

    Pattern pattern = Pattern.compile("&#(\\d{5});&#(\\d{5});");
    Matcher matcher = pattern.matcher(str);
    while (matcher.find()) {
      String found = matcher.group();
      int h = Integer.parseInt(matcher.group(1));
      int l = Integer.parseInt(matcher.group(2));
      if (0xD800 <= h && h < 0xDC00 && 0xDC00 <= l && l < 0xDFFF) {
        int n = (h - 0xD800) * 0x400 + (l - 0xDC00) + 0x10000;
        str = str.replace(found, "&#" + n + ";");
      }
    }
    pattern = Pattern.compile("&#(\\d{5});");
    matcher = pattern.matcher(str);
    while (matcher.find()) {
      String found = matcher.group();
      int n = Integer.parseInt(matcher.group(1));
      if (0xD800 <= n && n < 0xDFFF) {
        str = str.replace(found, "");
      }
    }
    return str;
  }

  public static String toString(final List<?> source) {
    if (source == null || source.size() == 0) {
      return null;
    }
    return source.stream()
        .map(e -> {
          if (e == null) {
            return "";
          }

          return String.valueOf(e);
        })
        .reduce((a, b) -> a.concat(",").concat(b))
        .orElse(null);
  }

  public static String toString(final String... source) {
    if (source == null || source.length == 0) {
      return null;
    }
    return Arrays
        .stream(source)
        .map(StringUtil::emptyIfNull)
        .reduce((a, b) -> a.concat(",").concat(b))
        .get();
  }

  public static String toString(final Number... source) {
    if (source == null || source.length == 0) {
      return null;
    }
    return Arrays
        .stream(source)
        .map(e -> e == null ? "" : String.valueOf(e))
        .reduce((a, b) -> a.concat(",").concat(b))
        .get();
  }

  public static String toString(final byte[] bytes) {
    if (bytes == null) {
      return null;
    }

    return new String(bytes, StandardCharsets.UTF_8);
  }

  public static byte[] toBytes(final String source) {
    if (source == null) {
      return null;
    }

    return source.getBytes(StandardCharsets.UTF_8);
  }

  public static boolean isBlank(final CharSequence cs) {
    return StringUtils.isBlank(cs);
  }

  public static String[] toArray(final String source) {
    if (isBlank(source)) {
      return null;
    }

    return source.split(",");
  }

  public static List<String> toStringList(final String source) {
    if (isBlank(source)) {
      return null;
    }

    return new ArrayList<>(Arrays.asList(source.split(",")));
  }

  public static Set<String> toStringSet(final String source) {
    if (isBlank(source)) {
      return null;
    }

    return new HashSet<>(Arrays.asList(source.split(",")));
  }

  public static String cleanPath(String path) {
    return org.springframework.util.StringUtils.cleanPath(path);
  }

  public static String emptyIfNull(String value) {
    return value == null ? "" : value;
  }

  public static int countMatches(final CharSequence str, final char ch) {
    return StringUtils.countMatches(str, ch);
  }

}