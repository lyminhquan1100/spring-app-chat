package spring.boot.module.auth.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.boot.core.api.CoreController;
import spring.boot.core.api.ResponseDTO;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.auth.dto.LoginDTO;
import spring.boot.module.auth.entity.AccountEntity;
import spring.boot.module.auth.service.AccountService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController extends CoreController<AccountDTO, AccountEntity,AccountService> {
    public AccountController(AccountService s) {
        super(s);
    }

    @PostMapping("/register")
    public ResponseDTO register(@RequestBody AccountDTO accountDTO) {
        return response(service.register(accountDTO));
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginDTO loginDTO, HttpServletRequest httpServletRequest) {
        return response(service.login(loginDTO));
    }

    @PutMapping("/avatar")
    public ResponseDTO avatar(@RequestParam("file") MultipartFile file) {
        return response(service.changeAvatar(file));
    }
}
