package spring.boot.module.chat.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.core.api.CoreController;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.entity.MessageEntity;
import spring.boot.module.chat.service.MessageService;

@RequestMapping("/message")
@RestController
@CrossOrigin
public class MessageController extends CoreController<MessageDTO, MessageEntity, MessageService> {

    public MessageController(MessageService s) {
        super(s);
    }
}
