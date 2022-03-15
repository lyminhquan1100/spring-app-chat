package spring.boot.core.dao.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import spring.boot.core.dao.model.BaseEntity;
import spring.boot.core.dto.BaseDTO;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<Entity extends BaseEntity,DTO extends BaseDTO,ID extends Long> extends CrudRepository<Entity,ID> {
  @Override
  @Transactional(readOnly = true)
  @Query("select e from #{#entityName} e where e.id = ?1")
  Optional<Entity> findById(ID id);

  @Transactional(readOnly = true)
  @Query("select e from #{#entityName} e")
  Page<Entity> search(DTO dto, Pageable pageable);

  @Override
  @CacheEvict(allEntries = true)
  @Transactional
  @Modifying
  <S extends Entity> S save(S entity);


  @Override
  @CacheEvict(allEntries = true)
  <S extends Entity> List<S> saveAll(Iterable<S> entities);

  @Cacheable
  @Override
  @Transactional(readOnly = true)
  @Query("select case when count(e) > 0 then true else false end from #{#entityName} e"
      + " where e.id = ?1")
  boolean existsById(ID id);

  @Override
  @Transactional
  @Modifying
  @Query(value = "update #{#entityName} e set e.deleted = 1 "
      + " where e.id = ?#{#id} ")
  void deleteById(ID id);

}
