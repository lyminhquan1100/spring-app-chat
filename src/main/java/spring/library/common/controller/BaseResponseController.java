package spring.library.common.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.util.CastUtils;
import spring.library.common.dto.ResponseEntity;

public class BaseResponseController {

  protected ResponseEntity<?> response(Object data) {
    ResponseEntity<?> responseBody = new ResponseEntity<>(data);
    responseBody.setCode(200);
    responseBody.setMessage("successful");

    if (data instanceof Page) {
      Page<?> page = CastUtils.cast(data);
      responseBody.setTotalElements(page.getTotalElements());
      responseBody.setNumberOfElements(page.getNumberOfElements());
      responseBody.setData(page.getContent());
    }
    return responseBody;
  }
}
