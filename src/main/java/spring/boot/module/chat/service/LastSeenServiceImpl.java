package spring.boot.module.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.core.api.CoreServiceImpl;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.auth.service.AccountService;
import spring.boot.module.chat.dto.LastSeenDTO;
import spring.boot.module.chat.entity.LastSeenEntity;
import spring.boot.module.chat.entity.MessageEntity;
import spring.boot.module.chat.enums.PackEnum;
import spring.boot.module.chat.repository.LastSeenRepository;
import spring.boot.module.chat.repository.MessageRepository;

@Service
public class LastSeenServiceImpl
        extends CoreServiceImpl<LastSeenDTO, LastSeenEntity>
        implements LastSeenService {

    @Autowired
    private LastSeenRepository lastSeenRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PackService packService;

    @Override
    public void lastSeen(Long userId, Long roomId, Long messageId) {
        LastSeenEntity lastSeenEntity = lastSeenRepository
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
        packService.sendToRoom(roomId, PackEnum.LAST_SEEN, mapToDTO(lastSeenEntity));
    }

    @Override
    public LastSeenDTO mapToDTO(LastSeenEntity entity) {
        LastSeenDTO dto = super.mapToDTO(entity);
        if (entity.getMessageEntity() != null) {
            dto.setRoomId(entity.getMessageEntity().getRoomId());
        } else if (entity.getMessageId() != null) {
            MessageEntity messageEntity = messageRepository
                    .findById(entity.getMessageId()).orElse(null);
            if (messageEntity != null) {
                dto.setRoomId(messageEntity.getRoomId());
            }
        }


        if (entity.getAccount() == null && entity.getUserId() != null) {
            AccountDTO accountDTO = accountService.getDetail(entity.getUserId());
            if (accountDTO != null) {
                dto.setAccount(accountDTO);
            }
        }
        return dto;
    }
}
