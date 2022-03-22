package spring.boot.module.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.core.controller.BaseController;
import spring.boot.core.dto.ResponseDTO;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.service.MessageService;
import spring.boot.module.chat.service.RoomService;

import java.security.Principal;
import java.util.List;

@RequestMapping("/message")
@RestController
@CrossOrigin
public class MessageController extends BaseController<MessageDTO, MessageService> {
    @Autowired
    private MessageService messageService;

    @Autowired
    private RoomService roomService;

    @Override
    public MessageService getService() {
        return messageService;
    }

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
}
