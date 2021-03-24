package spring.backend.library.controller;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import spring.backend.library.dto.BaseDTO;
import spring.backend.library.dto.ResponseEntity;
import spring.backend.library.service.BaseService;

public abstract class BaseController<DTO extends BaseDTO, Service extends BaseService<DTO>> extends
BaseResponseController{

  public abstract Service getService();

  @GetMapping
  public ResponseEntity<?> search(DTO dto,
      @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
    return response(getService().search(dto,pageable));
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody DTO dto){
    return response(getService().save(dto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id,@RequestBody DTO dto){
    return response(getService().save(id,dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id){
    getService().delete(id);
    return response(null);
  }
}
