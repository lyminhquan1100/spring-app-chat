package spring.backend.library.map;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.CastUtils;
import spring.backend.library.dao.model.BaseEntity;
import spring.backend.library.dto.BaseDTO;
import spring.backend.library.utils.DateUtils;
import spring.backend.library.exception.BaseException;

public abstract class MapperService<Entity extends BaseEntity,DTO extends BaseDTO> {
  private final Class<Entity> entityClass;
  private final Class<DTO> dtoClass;
  private final ModelMapper modelMapper;

  public MapperService() {
    entityClass = CastUtils.cast(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    dtoClass = CastUtils.cast(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
    modelMapper = Mapper.getDefaultModelMapper();
    config(modelMapper);

    getModelMapper().map(getDTO(),getEntity());
    getModelMapper().map(getEntity(),getDTO());

  }
  protected void config(ModelMapper mapper){

  }

  protected Entity getEntity() {
    try {
      return entityClass.getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException
        | NoSuchMethodException e) {
      throw new BaseException("error get entity");
    }
  }

  protected DTO getDTO() {
    try {
      return dtoClass.getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException
        | NoSuchMethodException e) {
      throw new BaseException("error get dto");
    }
  }

  protected Class<Entity> getEntityClass() {
    return entityClass;
  }

  protected Class<DTO> getDtoClass() {
    return dtoClass;
  }

  public String getName(){
    return entityClass.getName();
  }

  protected ModelMapper getModelMapper(){
    return modelMapper;
  }

  public Entity mapToEntity(DTO dto){
    Entity entity = getModelMapper().map(dto,getEntityClass());
    specificMapToEntity(dto,entity);
    return entity;
  }
  public void mapToEntity(DTO dto,Entity entity){
    getModelMapper().map(dto,entity);
    specificMapToEntity(dto, entity);
  }
  public DTO mapToDTO(Entity entity){
    DTO dto = getModelMapper().map(entity,getDtoClass());
    specificMapToDTO(entity,dto);
    return dto;
  }
  protected void mapToDTO(Entity entity,DTO dto){
    getModelMapper().map(entity,dto);
    specificMapToDTO(entity,dto);
  }

  protected void specificMapToDTO(Entity entity,DTO dto){
    dto.setCreatedAt(
        DateUtils.convertLocalDateTimeToString(entity.getCreatedAt(),"yyyy-MM-dd HH:mm:ss"));
    dto.setUpdatedAt(
        DateUtils.convertLocalDateTimeToString(entity.getUpdatedAt(),"yyyy-MM-dd HH:mm:ss"));
  }
  protected void specificMapToEntity(DTO dto,Entity entity){
//    entity.setAuditProperties(
//        convertStringToLocalDate(dto.getCreatedAt(), "yyyy-MM-dd HH:mm:ss z"),
//        convertStringToLocalDate(dto.getUpdatedAt(),"yyyy-MM-dd HH:mm:ss z"));
  }

}
