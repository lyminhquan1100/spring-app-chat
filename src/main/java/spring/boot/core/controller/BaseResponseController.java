package spring.boot.core.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.util.CastUtils;
import spring.boot.core.dto.ResponseDTO;

public class BaseResponseController {

  protected ResponseDTO response(Object data) {
    ResponseDTO responseBody = new ResponseDTO(data);
    if (data instanceof ResponseDTO){
      responseBody = (ResponseDTO) data;
    }
    else {
      responseBody.setCode(0);
      responseBody.setMessage("successful");
    }
    if (data instanceof Page) {
      Page<?> page = CastUtils.cast(data);
      responseBody.setTotalPages(page.getTotalPages());
      responseBody.setTotalElements(page.getTotalElements());
      responseBody.setPageSize(page.getSize());
      responseBody.setPageNumber(page.getNumber());
      responseBody.setNumberOfElements(page.getNumberOfElements());
      responseBody.setData(page.getContent());
    }
    return responseBody;
  }
}
