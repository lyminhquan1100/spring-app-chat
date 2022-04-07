package spring.boot.core.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CoreService
        <DTO extends CoreDTO,
        Entity extends CoreEntity> {
    Page<DTO> search(DTO dto, Pageable pageable);

    DTO save(DTO dto);

    Entity save(Entity entity);

    DTO save(Long id, Map<String, Object> dto);

    List<DTO> save(List<DTO> dtos);

    DTO mapToDTO(Entity entity);

    Entity getById(Long id);

    void delete(Long id);

    DTO getDetail(Long id);

    DTO findById(Long id);
}
