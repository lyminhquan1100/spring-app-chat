package spring.boot.module.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring.boot.core.config.dto.UsernameAndPasswordDTO;
import spring.boot.core.config.filter.JwtProvider;
import spring.boot.core.config.filter.JwtProvider.JwtTokenProperties;
import spring.boot.core.config.userdetail.UserPrincipal;
import spring.boot.core.exception.BaseException;
import spring.boot.core.service.AbstractBaseService;
import spring.boot.core.utils.DigestUtil;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.auth.entity.AccountEntity;
import spring.boot.module.auth.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl extends AbstractBaseService<AccountEntity, AccountDTO, AccountRepository> implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected AccountRepository getRepository() {
        return accountRepository;
    }

    @Override
    public void mapToEntity(AccountDTO dto, AccountEntity entity) {
        if (dto.getId() != null) {
            dto.setUsername(entity.getUsername());
            dto.setPassword(entity.getPassword());
            if(dto.getRole() == null){
                dto.setRole(entity.getRole());
            }
        }
        super.mapToEntity(dto, entity);
    }

    @Override
    public Map<String, Object> login(UsernameAndPasswordDTO dto) {
        if (dto.getPassword() == null || dto.getUsername() == null) {
            throw new BaseException(400, "Chưa nhập tên đăng nhập hoặc mật khẩu");
        }
        AccountEntity userEntity = getRepository().findByUsername(dto.getUsername());
        if (userEntity == null) {
            throw new BaseException(400, "Tên đăng nhập không tồn tại");
        }

        if (!DigestUtil.sha256Hex(dto.getPassword()).equals(userEntity.getPassword())) {
            throw new BaseException(400, "password không chính xác");
        }

        List<String> roles = new ArrayList<>();

        if (userEntity.getRole() != null) {
            roles.add("ROLE_" + userEntity.getRole());
        }

        JwtTokenProperties jwts = JwtTokenProperties.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .fullName(userEntity.getFullName())
                .email(userEntity.getEmail())
                .role(roles.get(0))
                .avatar(userEntity.getAvatar())
                .privileges(roles)
                .build();

        return jwtProvider.generateToken(jwts);
    }

    @Override
    public AccountDTO register(AccountDTO accountDTO) {
        if (accountDTO.getPassword() == null || accountDTO.getUsername() == null) {
            throw new BaseException(400, "Chưa nhập username và password");
        }
        if (getRepository().existsByUsername(accountDTO.getUsername())) {
            throw new BaseException("Tên đăng nhập đã tồn tại");
        }
        AccountEntity accountEntity = mapToEntity(accountDTO);

        accountEntity.setRole(3L);
        accountEntity.setPassword(DigestUtil.sha256Hex(accountDTO.getPassword()));
        save(accountEntity, accountDTO);

        return accountDTO;
    }


    @Override
    public AccountDTO getCurrentUser() {
        return findById(getCurrentUserId());
    }

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        UserPrincipal userDetail = (UserPrincipal) authentication.getPrincipal();
        return userDetail.getId();
    }
}
