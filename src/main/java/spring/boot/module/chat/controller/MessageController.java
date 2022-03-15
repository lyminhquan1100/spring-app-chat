package spring.boot.module.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.core.controller.BaseController;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.service.MessageService;

import java.security.Principal;
import java.util.List;

@RequestMapping("/message")
@RestController
@CrossOrigin
public class MessageController extends BaseController<MessageDTO, MessageService> {
    @Autowired
    private MessageService messageService;

    @Override
    public MessageService getService() {
        return messageService;
    }


    @SubscribeMapping("/old.messages")
    public List<MessageDTO> listOldMessagesFromUserOnSubscribe(SimpMessageHeaderAccessor headerAccessor) {
        Long chatRoomId = (Long) headerAccessor.getSessionAttributes().get("chatRoomId");
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        return messageService.findByUserIdAndRoomId(userId, chatRoomId);
    }

}
