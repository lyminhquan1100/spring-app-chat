package spring.boot.module.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.core.service.AbstractBaseService;
import spring.boot.module.auth.entity.AccountEntity;
import spring.boot.module.auth.repository.AccountRepository;
import spring.boot.module.auth.service.AccountService;
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

    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected MessageRepository getRepository() {
        return messageRepository;
    }

    @Override
    public List<MessageDTO> findByUserIdAndRoomId(Long userId, Long roomId) {
        return messageRepository.findAllByCreatedByAndRoomId(userId, roomId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    protected void specificMapToDTO(MessageEntity entity, MessageDTO dto) {
        super.specificMapToDTO(entity, dto);
        if (entity.getFromUser() != null) {
            dto.setFullName(entity.getFromUser().getFullName());
            dto.setAvatar(entity.getFromUser().getAvatar());
        } else if (entity.getFromId() != null) {
            AccountEntity accountEntity = accountRepository.findById(entity.getFromId()).orElse(null);
            if (accountEntity != null) {
                dto.setFullName(accountEntity.getFullName());
                dto.setAvatar(accountEntity.getAvatar());
            }
        }
    }
}
