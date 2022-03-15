package spring.boot.core.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import spring.boot.core.dto.BaseDTO;
import spring.boot.core.dto.ResponseDTO;
import spring.boot.core.service.BaseService;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin
public abstract class BaseController<DTO extends BaseDTO, Service extends BaseService<DTO>> extends
BaseResponseController{

  public abstract Service getService();

  @GetMapping("/{id}")
  public ResponseDTO getDetail(@PathVariable Long id) {
    return response(getService().findDetailById(id));
  }

  @GetMapping
  public ResponseDTO search(DTO dto,
                            @PageableDefault(size = 200, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
    return response(getService().search(dto,pageable));
  }

  @PostMapping
  public ResponseDTO create(@Valid @RequestBody DTO dto){
    return response(getService().save(dto));
  }

  @PutMapping("/{id}")
  public ResponseDTO update(@PathVariable Long id, @RequestBody DTO dto){
    return response(getService().save(id,dto));
  }

  @DeleteMapping("/{id}")
  public ResponseDTO delete(@PathVariable Long id){
    getService().delete(id);
    return response(null);
  }

  @PatchMapping("/{id}")
  public ResponseDTO update(@PathVariable Long id, @RequestBody Map<String,Object> dto){
    return response(getService().save(id, dto));
  }
}
