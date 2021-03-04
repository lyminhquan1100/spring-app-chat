//package spring.library.common.service;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.ParameterizedType;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import org.modelmapper.ModelMapper;
//import org.springframework.data.util.CastUtils;
//import spring.library.common.dao.model.BaseEntity;
//import spring.library.common.dto.BaseDTO;
//import spring.library.common.exception.BaseException;
//
//public abstract class AbstractBaseMapService<Entity extends BaseEntity, DTO extends BaseDTO> {
//
//  private final Class<Entity> entityClass;
//
//  private final Class<DTO> dtoClass;
//
//  private final ModelMapper modelMapper;
//
//  public AbstractBaseMapService() {
//
//    entityClass = CastUtils.cast(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
//    dtoClass = CastUtils.cast(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
//    modelMapper = ModelMapperUtil.getDefaultModelMapper();
//    configModelMapper(modelMapper);
//
//    getModelMapper().map(getDTO(), getEntity());
//    getModelMapper().map(getEntity(), getDTO());
//
//  }
//
//  final protected ModelMapper getModelMapper() {
//    return modelMapper;
//  }
//
//  protected void configModelMapper(ModelMapper modelMapper) {
//
//  }
//
//  final protected Class<Entity> getEntityClass() {
//    return entityClass;
//  }
//
//  final protected Class<DTO> getDTOClass() {
//    return dtoClass;
//  }
//
//  protected Entity getEntity() {
//    try {
//      return entityClass.getConstructor().newInstance();
//    } catch (InstantiationException | IllegalAccessException | InvocationTargetException
//        | NoSuchMethodException e) {
//      throw new BaseException("exception");
//    }
//  }
//
//  protected DTO getDTO() {
//    try {
//      return dtoClass.getConstructor().newInstance();
//    } catch (InstantiationException | IllegalAccessException | InvocationTargetException
//        | NoSuchMethodException e) {
//      throw new BaseException("exception");
//    }
//  }
//
//  final public String getName() {
//    return "Msg.getMessage(getSimpleName())";
//  }
//
//  final public String getSimpleName() {
//    return entityClass.getSimpleName();
//  }
//
//  final protected Entity mapToEntity(DTO dto) {
//    if (dto == null) {
//      return null;
//    }
//
//    if (dto.getActive() == null && dto.getId() != null && dto.getId() > 0) {
//      dto.setActive(getActiveById(dto.getId()));
//    }
//
//    Entity entity = getModelMapper().map(dto, getEntityClass());
//
//    specificMapToEntity(dto, entity);
//
//
//    return entity;
//  }
//
//  final protected void mapToEntity(DTO dto, Entity entity) {
//    if (dto == null) {
//      return;
//    }
//
//    long startTime = 0;
//
//    if (dto.getActive() == null && entity.getActive() != null
//        && entity.getId() != null && entity.getId() > 0) {
//      dto.setActive(entity.getActive());
//    }
//
//    getModelMapper().map(dto, entity);
//
//    specificMapToEntity(dto, entity);
//
//  }
//
//  final protected Set<Entity> mapToEntities(Set<DTO> dtos, Set<Entity> entities) {
//    if (entities == null) {
//      entities = new HashSet<>();
//    }
//
//    if (dtos == null) {
//      dtos = new HashSet<>();
//    }
//
//
//    List<Entity> removeEntities = new ArrayList<>();
//
//    for (Entity entity : entities) {
//      DTO dto = dtos.stream().filter(d -> entity.getId().equals(d.getId())).findFirst().orElse(null);
//
//      if (dto == null) {
//        removeEntities.add(entity);
//      } else {
//        mapToEntity(dto, entity);
//
//        dtos.remove(dto);
//      }
//    }
//
//    for (Entity e: removeEntities) {
//      entities.remove(e);
//      deleteEntity(e);
//    }
//
//    for (DTO dto: dtos) {
//      entities.add(mapToEntity(dto));
//    }
//
//    return entities;
//  }
//
//  final public DTO mapToDTO(Entity entity) {
//    if (entity == null) {
//      return null;
//    }
//
//    long startTime = 0;
//
//    DTO dto = getModelMapper().map(entity, getDTOClass());
//
//    specificMapToDTO(entity, dto);
//
//
//    return dto;
//  }
//
//  final protected void mapToDTO(Entity entity, DTO dto) {
//    if (entity == null) {
//      return;
//    }
//
//    long startTime = 0;
//
//    getModelMapper().map(entity, dto);
//
//    specificMapToDTO(entity, dto);
//  }
//
//  protected void specificMapToDTO(Entity entity, DTO dto) {
//
//  }
//
//  protected void specificMapToEntity(DTO dto, Entity entity) {
//
//  }
//
//  protected Boolean getActiveById(Long id) {
//    return null;
//  }
//
//  protected Entity deleteEntity(Entity model) {
//    return null;
//  }
//}