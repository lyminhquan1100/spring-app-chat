package spring.boot.core.msg;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class Message {
  private static ReloadableResourceBundleMessageSource messageSource;

  private static MessageSource getMessageSource(){
    if (messageSource != null)
      return messageSource;

    messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.addBasenames("classpath:msg/common_messages");
    messageSource.setDefaultEncoding("UTF-8");
    LocaleContextHolder.setDefaultLocale(Message.getLocaleDefault());

    return messageSource;
  }
  public static String getMessage(String key, Object[] args, Locale locale,
      HttpServletRequest request) {
    if (request != null) {
      String headerLang = request.getHeader("Accept-Language");
      if (headerLang == null) {
        locale = getLocaleDefault();
      }
    }

    if (locale == null) {
      locale = LocaleContextHolder.getLocale();
    }

    return getMessageSource().getMessage(key, args, key, locale);
  }

  public static String getMessage(String key,Object[] args){
    return getMessage(key,args,null,null);
  }
  public static String getMessage(String key){
    return getMessage(key,null,null,null);
  }
  public static String getMessage(String key, HttpServletRequest request){
    return getMessage(key,null,null,request);
  }

  public static Locale getLocaleDefault() {
    return new Locale("vi", "VI");
  }
}
