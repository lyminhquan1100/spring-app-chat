package spring.boot.module.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.core.service.AbstractBaseService;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.entity.MessageEntity;
import spring.boot.module.chat.repository.MessageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl
        extends AbstractBaseService<MessageEntity, MessageDTO, MessageRepository>
        implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    protected MessageRepository getRepository() {
        return messageRepository;
    }

    @Override
    public List<MessageDTO> findByUserIdAndRoomId(Long userId, Long roomId) {
        return messageRepository.findAllByCreatedByAndRoomId(userId, roomId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
