package spring.boot.module.auth.service;

import spring.boot.core.config.dto.UsernameAndPasswordDTO;
import spring.boot.core.service.BaseService;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.auth.entity.AccountEntity;

import java.util.Map;

public interface AccountService extends BaseService<AccountDTO> {
    Map<String,Object> login(UsernameAndPasswordDTO dto);

    AccountDTO register(AccountDTO account);

    AccountDTO getCurrentUser();

    Long getCurrentUserId();

    AccountEntity mapToEntity(AccountDTO accountDTO);

    AccountDTO mapToDTO(AccountEntity accountEntity);

    AccountDTO changeAvatar(AccountDTO accountDTO);
}
