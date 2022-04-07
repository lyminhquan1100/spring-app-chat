package spring.boot.core.api;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoreRepository<DTO extends CoreDTO, Entity extends CoreEntity>
        extends CrudRepository<Entity, Long> {
    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.id = ?1")
    Optional<Entity> findById(Long id);

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
    boolean existsById(Long id);

    @Override
    @Transactional
    @Modifying
    @Query(value = "update #{#entityName} e set e.deleted = 1 "
            + " where e.id = ?#{#id} ")
    void deleteById(Long id);

}
