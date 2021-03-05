package spring.library.common.service;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.library.common.dto.BaseDTO;

public interface BaseService<DTO extends BaseDTO> {
  Page<DTO> search(DTO dto, Pageable pageable);
  DTO save(DTO dto);
  DTO save(Long id,DTO dto);
  DTO save(Long id, Map<String,Object> map);
  void delete(Long size);
}
