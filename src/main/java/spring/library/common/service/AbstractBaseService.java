package spring.library.common.service;

import javax.transaction.Transactional;
import spring.library.common.dao.model.BaseEntity;
import spring.library.common.dao.repository.BaseRepository;
import spring.library.common.dto.BaseDTO;
import spring.library.common.dto.ResponseEntity;

@Transactional
public abstract class AbstractBaseService<Entity extends BaseEntity,DTO extends BaseDTO,
    Repository extends BaseRepository<Entity,Long>>
  extends MapperService<Entity,DTO> implements BaseService<DTO> {

  protected abstract Repository getRepository();

  @Override
  public ResponseEntity<?> search(DTO dto, Integer page, Integer size) {
    return new ResponseEntity<>(getRepository().findAll());
  }

  @Override
  public ResponseEntity<DTO> create(DTO dto) {

    Entity entity = mapToEntity(dto);

    getRepository().save(entity);

    return new ResponseEntity<>(mapToDTO(entity));
  }

  @Override
  public ResponseEntity<DTO> update(Long id, DTO dto) {
    return null;
  }

  @Override
  public ResponseEntity<DTO> delete(Long size) {
    return null;
  }
}
