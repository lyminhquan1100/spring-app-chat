package spring.boot.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import spring.boot.core.exception.BaseException;
import spring.boot.core.exception.DataException;
import spring.boot.core.msg.Message;
import spring.boot.core.utils.MapperUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
public class CoreServiceImpl<DTO extends CoreDTO, Entity extends CoreEntity>
        extends CoreServiceMapper<DTO, Entity>
        implements CoreService<DTO, Entity> {
    @Autowired
    private CoreRepository<DTO, Entity> repository;

    protected CoreRepository<DTO, Entity> getRepository() {
        return repository;
    }

    @Override
    @Cacheable
    public Page<DTO> search(DTO dto, Pageable pageable) {
        return getRepository().search(dto, pageable).map(this::mapToDTO);
    }

    protected void beforeSave(Entity entity, DTO dto) {
    }

    protected void afterSave(Entity entity, DTO dto) {
    }

    @Override
    public DTO save(DTO dto) {
        Entity entity;
        if (dto.getId() != null) {
            entity = getById(dto.getId());
            getModelMapper().map(dto, entity);
            entity.setId(dto.getId());
        } else {
            entity = mapToEntity(dto);
        }

        entity = save(entity,dto);
        return mapToDTO(entity);
    }

    @Override
    public Entity save(Entity entity) {
        return save(entity, mapToDTO(entity));
    }

    @Override
    public List<DTO> save(List<DTO> dtos) {
        List<Entity> entities = new ArrayList<>();

        dtos.forEach(dto -> {
            Entity entity;
            if (dto.getId() != null) {
                entity = getById(dto.getId());
                getModelMapper().map(dto, entity);
                entity.setId(dto.getId());
            } else {
                entity = mapToEntity(dto);
            }
            entities.add(entity);
        });

        return save(entities, dtos);
    }

    protected void beforeSave(List<Entity> entities, List<DTO> dtos) {
        int size = entities.size();
        for (int i = 0; i < size; i++) {
            Entity entity = entities.get(i);
            DTO dto = dtos.get(i);
            beforeSave(entity, dto);
        }
    }

    protected void afterSave(List<Entity> entities, List<DTO> Ds) {
        int size = entities.size();
        for (int i = 0; i < size; i++) {
            Entity E = entities.get(i);
            DTO D = Ds.get(i);
            afterSave(E, D);
        }
    }

    @Override
    public DTO save(Long id, Map<String, Object> map) {
        if (id == null || id.compareTo(0L) <= 0) {
            throw new DataException.NotFoundEntityById(id, getName());
        }

        if (map == null) {
            throw new BaseException(400, "json is null");
        }

        Entity model = getById(id);

        map = mergeMap(map, MapperUtils.convertValue(mapToDTO(model)));
        DTO dto = MapperUtils.convertValue(map, getDtoClass());

        dto.setId(id);
        getModelMapper().map(dto, model);
        model.setId(id);

        return mapToDTO(save(model, dto));
    }

    protected List<DTO> save(List<Entity> entities, List<DTO> dtos) {
        beforeSave(entities, dtos);

        entities = getRepository().saveAll(entities);

        int size = entities.size();

        afterSave(entities, dtos);

        for (int i = 0; i < size; i++) {
            Entity entity = entities.get(i);
            dtos.set(i, mapToDTO(entity));
        }

        return dtos;
    }

    protected Entity save(Entity entity, DTO dto) {
        beforeSave(entity, dto);

        Entity e = getRepository().save(entity);

        afterSave(e, dto);

        return e;
    }

    @Override
    public DTO getDetail(Long id) {
        Entity E = getRepository().findById(id).get();
        return mapToDTO(E);
    }

    @Override
    public void delete(Long id) {
        getRepository().deleteById(id);
    }

    public Entity getById(Long id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new DataException.NotFoundEntityById(id, getName()));
    }

    @Override
    public DTO findById(Long id) {
        Entity e = getById(id);
        return mapToDTO(e);
    }

    private Map<String, Object> mergeMap(Map<String, Object> from, Map<String, Object> to) {
        from.forEach((key, newValue) -> {
            Object oldValue = to.get(key);
            if ((oldValue instanceof Map) && (newValue instanceof Map)) {
                to.put(key, mergeMap(CastUtils.cast(newValue), CastUtils.cast(oldValue)));
            } else {
                to.put(key, newValue);
            }
        });
        return to;
    }

    protected String getName() {
        return Message.getMessage(getClass().getName());
    }
}
