package spring.boot.module.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.boot.core.config.dto.UsernameAndPasswordDTO;
import spring.boot.core.controller.BaseController;
import spring.boot.core.dto.ResponseDTO;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.auth.service.AccountService;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController extends BaseController<AccountDTO, AccountService> {
    @Autowired
    private AccountService service;

    @Override
    public AccountService getService() {
        return service;
    }

    @PostMapping("/register")
    public ResponseDTO register(@RequestBody AccountDTO accountDTO) {
        return response(getService().register(accountDTO));
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody UsernameAndPasswordDTO usernameAndPasswordDTO) {
        return response(getService().login(usernameAndPasswordDTO));
    }

    @PutMapping("/avatar")
    public ResponseDTO avatar(@RequestBody AccountDTO accountDTO) {
        return response(getService().changeAvatar(accountDTO));
    }
}
