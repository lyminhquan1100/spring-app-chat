package spring.backend.library.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
  public static String convertLocalDateTimeToString(ZonedDateTime dateTime, String patten) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patten);
    String formattedDateTime = dateTime.format(formatter); // "1986-04-08 12:30"
    return formattedDateTime;
  }
  public static ZonedDateTime convertStringToLocalDate(String timeString, String pattern) {
    if(timeString==null) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    ZonedDateTime date = ZonedDateTime.parse(timeString, formatter);
    return date;
  }
}
