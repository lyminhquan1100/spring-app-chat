package spring.library.common.dao.repository;

import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import spring.library.common.dao.model.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<Entity extends BaseEntity,ID extends Long> extends CrudRepository<Entity,ID> {
  @Override
  @Transactional(readOnly = true)
  @Query("select e from #{#entityName} e where e.id = ?1")
  Optional<Entity> findById(ID id);

  @Override
  @CacheEvict(allEntries = true)
  @Transactional
  @Modifying
  <S extends Entity> S save(S entity);

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
