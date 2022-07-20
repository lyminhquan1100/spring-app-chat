package spring.boot.module.chat.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.core.api.CoreController;
import spring.boot.module.chat.dto.LastSeenDTO;
import spring.boot.module.chat.entity.LastSeenEntity;
import spring.boot.module.chat.service.LastSeenService;

@RequestMapping("/last-seen")
@RestController
@CrossOrigin
public class LastSeenController extends CoreController<LastSeenDTO, LastSeenEntity, LastSeenService> {

    public LastSeenController(LastSeenService s) {
        super(s);
    }
}
