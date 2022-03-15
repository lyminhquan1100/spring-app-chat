package spring.boot.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class ResponseUtil {

  public static void writeResponse(HttpStatus httpStatus, HttpServletResponse response,
      Object responseMsg)
      throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(httpStatus.value());
    response.getWriter().write(responseMsg.toString());
  }

  public static String convertObjectToJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }
}
