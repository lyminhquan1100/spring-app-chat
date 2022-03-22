package spring.boot.module.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.core.service.AbstractBaseService;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.auth.service.AccountService;
import spring.boot.module.chat.dto.LastSeenDTO;
import spring.boot.module.chat.entity.LastSeenEntity;
import spring.boot.module.chat.entity.MessageEntity;
import spring.boot.module.chat.repository.LastSeenRepository;
import spring.boot.module.chat.repository.MessageRepository;

@Service
public class LastSeenServiceImpl
        extends AbstractBaseService<LastSeenEntity, LastSeenDTO, LastSeenRepository>
        implements LastSeenService {

    @Autowired
    private LastSeenRepository lastSeenRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoomService roomService;

    @Override
    protected LastSeenRepository getRepository() {
        return lastSeenRepository;
    }

    @Override
    public void lastSeen(Long userId, Long roomId, Long messageId) {
        LastSeenEntity lastSeenEntity = getRepository()
                .findLastSeenFromRoomId(userId, roomId);
        if (lastSeenEntity != null) {
            if (lastSeenEntity.getMessageId() == messageId) {
                return;
            }
            lastSeenEntity.setMessageId(messageId);
            getRepository().save(lastSeenEntity);
        } else {
            lastSeenEntity = new LastSeenEntity();
            lastSeenEntity.setUserId(userId);
            lastSeenEntity.setMessageId(messageId);
            getRepository().save(lastSeenEntity);
        }
        roomService.sendMessage(roomId, "lastSeen", mapToDTO(lastSeenEntity));
    }

    @Override
    protected void specificMapToDTO(LastSeenEntity entity, LastSeenDTO dto) {
        super.specificMapToDTO(entity, dto);
        if(entity.getMessageEntity() != null){
            dto.setRoomId(entity.getMessageEntity().getRoomId());
        }else if(entity.getMessageId() != null){
            MessageEntity messageEntity = messageRepository
                    .findById(entity.getMessageId()).orElse(null);
            if(messageEntity != null){
                dto.setRoomId(messageEntity.getRoomId());
            }
        }


        if(entity.getAccount() == null && entity.getUserId() != null){
            AccountDTO accountDTO = accountService.findById(entity.getUserId());
            if(accountDTO != null){
                dto.setAccount(accountDTO);
            }
        }
    }
}
