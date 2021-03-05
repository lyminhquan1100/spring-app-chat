package spring.library.common.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;
import spring.library.common.dao.model.BaseEntity;
import spring.library.common.dto.BaseDTO;
import spring.library.common.exception.BaseException;

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
    return getModelMapper().map(dto,getEntityClass());
  }
  public void mapToEntity(DTO dto,Entity entity){
    getModelMapper().map(dto,entity);
  }
  public DTO mapToDTO(Entity entity){
    return getModelMapper().map(entity,getDtoClass());
  }
  protected void mapToDTO(Entity entity,DTO dto){
    getModelMapper().map(entity,dto);
  }
}
