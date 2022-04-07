package spring.boot.module.chat.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.core.api.CoreController;
import spring.boot.module.chat.dto.EmojiDTO;
import spring.boot.module.chat.entity.EmojiEntity;

@RequestMapping("/react")
@RestController
@CrossOrigin
public class EmojiController extends CoreController<EmojiDTO, EmojiEntity> {

}
