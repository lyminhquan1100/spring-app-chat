package spring.boot.module.auth.service;

import org.springframework.web.multipart.MultipartFile;
import spring.boot.core.api.CoreService;
import spring.boot.core.security.dto.UsernameAndPasswordDTO;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.auth.dto.LoginDTO;
import spring.boot.module.auth.entity.AccountEntity;

import java.util.Map;

public interface AccountService extends CoreService<AccountDTO,AccountEntity> {
    Map<String,Object> login(LoginDTO dto);

    Boolean verifyUser(String username,String password);

    AccountDTO register(AccountDTO account);

    AccountDTO getCurrentUser();

    Long getCurrentUserId();

    AccountDTO changeAvatar(MultipartFile file);
}
