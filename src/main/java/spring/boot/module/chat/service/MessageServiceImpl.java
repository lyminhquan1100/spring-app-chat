package spring.boot.module.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.core.api.CoreServiceImpl;
import spring.boot.module.auth.entity.AccountEntity;
import spring.boot.module.auth.repository.AccountRepository;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.entity.MessageEntity;
import spring.boot.module.chat.entity.RoomEntity;
import spring.boot.module.chat.enums.PackEnum;
import spring.boot.module.chat.repository.RoomRepository;

@Service
public class MessageServiceImpl
        extends CoreServiceImpl<MessageDTO, MessageEntity>
        implements MessageService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PackService packService;

    @Override
    public MessageDTO mapToDTO(MessageEntity entity) {
        MessageDTO dto = super.mapToDTO(entity);
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
        return dto;
    }

    @Override
    public void sendMessage(MessageDTO messageDTO) {
        RoomEntity roomEntity = roomService.getById(messageDTO.getRoomId());
        MessageDTO messDTO = save(messageDTO);

        roomEntity.setLastMessageId(messDTO.getId());
        roomService.save(roomEntity);

        packService.sendToRoom(roomEntity, PackEnum.CHAT, messDTO);
    }
}
