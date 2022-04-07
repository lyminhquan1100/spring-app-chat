package spring.boot.module.chat.service;

import spring.boot.core.api.CoreService;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.entity.MessageEntity;

public interface MessageService extends CoreService<MessageDTO,MessageEntity> {
    void sendMessage(MessageDTO messageDTO);
}
