package spring.boot.core.api;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.util.CastUtils;
import spring.boot.core.exception.BaseException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public abstract class CoreServiceMapper<DTO extends CoreDTO,Entity extends CoreEntity> {
    private final Class<Entity> entityClass;
    private final Class<DTO> dtoClass;
    private final ModelMapper modelMapper;

    public CoreServiceMapper() {
        dtoClass = CastUtils.cast(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        entityClass = CastUtils.cast(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
        modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        getModelMapper().map(getDTO(),getEntity());
        getModelMapper().map(getEntity(),getDTO());
    }

    protected ModelMapper getModelMapper(){
        return modelMapper;
    }

    public Class<Entity> getEntityClass() {
        return entityClass;
    }

    public Class<DTO> getDtoClass() {
        return dtoClass;
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

    public Entity mapToEntity(DTO dto) {
        return getModelMapper().map(dto, getEntityClass());
    }

    public DTO mapToDTO(Entity entity) {
        return getModelMapper().map(entity, getDtoClass());
    }
}
