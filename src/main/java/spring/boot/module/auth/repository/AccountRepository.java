package spring.boot.module.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.boot.core.api.CoreRepository;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.auth.entity.AccountEntity;

@Repository
public interface AccountRepository extends CoreRepository<AccountDTO, AccountEntity> {

    @Override
    @Query("select e from AccountEntity e" +
            " where (lower(e.username) like %:#{#dto.username}% or :#{#dto.username} is null) " +
            " and (lower(e.fullName) like %:#{#dto.fullName}% or :#{#dto.fullName} is null) " +
            " and (e.role = :#{#dto.role} or :#{#dto.role} is null) "
    )
    Page<AccountEntity> search(AccountDTO dto, Pageable pageable);

    @Query("select case when count(e) > 0 then true else false end from AccountEntity e"
            + " where e.username = :#{#username}"
            + " and (e.id <> :#{#id} or :#{#id} is null)")
    boolean existsByUsernameNotPassword(String username, Long id);

    boolean existsByUsername(String username);

    AccountEntity findByUsername(String username);
}
