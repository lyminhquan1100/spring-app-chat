package spring.library.common.msg;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class Message {
  private static ReloadableResourceBundleMessageSource messageSource;

  private static MessageSource getMessageSource(){
    if (messageSource != null)
      return messageSource;

    messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setDefaultEncoding("UTF-8");

    return messageSource;
  }

  public static String getMessage(String key,Object[] args){
    return getMessageSource().getMessage(key,args,key,null);
  }
  public static String getMessage(String key){
    return getMessageSource().getMessage(key,null,null,null);
  }
}
