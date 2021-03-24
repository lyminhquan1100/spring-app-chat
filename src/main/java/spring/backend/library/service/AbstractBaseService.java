package spring.backend.library.service;

import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.backend.library.dao.model.BaseEntity;
import spring.backend.library.dao.repository.BaseRepository;
import spring.backend.library.dto.BaseDTO;
import spring.backend.library.exception.DataException.NotExistData;
import spring.backend.library.exception.DataException.NotFoundEntityById;
import spring.backend.library.map.MapperService;

@Transactional
public abstract class AbstractBaseService<Entity extends BaseEntity,DTO extends BaseDTO,
    Repository extends BaseRepository<Entity,DTO,Long>>
  extends MapperService<Entity,DTO> implements BaseService<DTO> {

  protected abstract Repository getRepository();

  @Override
  @Cacheable
  public Page<DTO> search(DTO dto, Pageable pageable) {
    return getRepository().search(dto,pageable).map(this::mapToDTO);
  }

  protected void beforeSave(Entity entity,DTO dto){
  }
  protected void afterSave(Entity entity,DTO dto){

  }
  @Override
  public DTO save(DTO dto) {
    if (dto == null){
      throw new NotExistData();
    }

    Entity entity;
    if (dto.getId() != null){
      entity = getById(dto.getId());
      mapToEntity(dto,entity);
      entity.setId(dto.getId());
    }
    else {
      entity = mapToEntity(dto);
    }

    return save(entity,dto);
  }

  @Override
  public DTO save(Long id, DTO dto) {
    if (dto == null){
      throw new NotExistData();
    }
    if (id == null || id.compareTo(0L) <= 0){
      throw new NotFoundEntityById(id,getName());
    }

    Entity model = getById(id);
    dto.setId(id);
    mapToEntity(dto,model);
    model.setId(id);

    return save(model,dto);
  }

  @Override
  public DTO save(Long id, Map<String, Object> map) {
    return null;
  }

  protected DTO save(Entity entity,DTO dto){
    beforeSave(entity,dto);

    getRepository().save(entity);

    afterSave(entity,dto);

    mapToDTO(entity,dto);

    return dto;
  }

  @Override
  public void delete(Long id) {
    getRepository().deleteById(id);
  }

  public Entity getById(Long id){
    return getRepository().findById(id)
        .orElseThrow(() -> new NotFoundEntityById(id, getName()));
  }
}
