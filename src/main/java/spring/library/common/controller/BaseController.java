package spring.library.common.controller;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import spring.library.common.dto.BaseDTO;
import spring.library.common.dto.ResponseEntity;
import spring.library.common.service.BaseService;

public abstract class BaseController<DTO extends BaseDTO,
    Service extends BaseService<DTO>> {

  public abstract Service getService();

  @GetMapping
  public ResponseEntity<?> search(DTO dto,Integer page,Integer size){
    return getService().search(dto,page,size);
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody DTO dto){
    return getService().create(dto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id,DTO dto){
    return getService().update(id,dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id){
    return getService().delete(id);
  }
}
