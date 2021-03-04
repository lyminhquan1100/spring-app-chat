package spring.library.common.service;

import spring.library.common.dto.BaseDTO;
import spring.library.common.dto.ResponseEntity;

public interface BaseService<DTO extends BaseDTO> {
  ResponseEntity<?> search(DTO dto,Integer page,Integer size);
  ResponseEntity<DTO> create(DTO dto);
  ResponseEntity<DTO> update(Long id, DTO dto);
  ResponseEntity<DTO> delete(Long size);
}
