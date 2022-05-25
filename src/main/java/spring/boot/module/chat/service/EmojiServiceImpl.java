package spring.boot.module.chat.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.core.api.CoreServiceImpl;
import spring.boot.module.auth.entity.AccountEntity;
import spring.boot.module.auth.repository.AccountRepository;
import spring.boot.module.chat.dto.EmojiDTO;
import spring.boot.module.chat.entity.EmojiEntity;
import spring.boot.module.chat.entity.MessageEntity;
import spring.boot.module.chat.enums.PackEnum;
import spring.boot.module.chat.repository.EmojiRepository;
import spring.boot.module.chat.repository.MessageRepository;

@Service
public class EmojiServiceImpl extends CoreServiceImpl<EmojiDTO, EmojiEntity> implements EmojiService {

    @Autowired
    private EmojiRepository repository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PackService packService;

    @Override
    public void sendEmoji(EmojiDTO emojiDTO) {
        EmojiEntity entity = repository.findByUserIdAndMessageId(emojiDTO.getUserId(), emojiDTO.getMessageId());
        emojiDTO.setRemove(entity != null);
        if (entity != null) {
            repository.delete(entity);
            packService.sendToRoom(emojiDTO.getRoomId(), PackEnum.EMOJI_REMOVE, emojiDTO);
        } else {
            EmojiDTO dto = save(emojiDTO);
            packService.sendToRoom(emojiDTO.getRoomId(), PackEnum.EMOJI, dto);
        }
    }

    @Override
    public EmojiDTO mapToDTO(EmojiEntity entity) {
        EmojiDTO dto = super.mapToDTO(entity);
        if (entity.getMessageEntity() != null) {
            dto.setRoomId(entity.getMessageEntity().getRoomId());
        } else {
            MessageEntity messageEntity = messageRepository.findById(entity.getMessageId()).orElse(null);
            if (messageEntity != null) {
                dto.setRoomId(messageEntity.getRoomId());
            }
        }

        if (entity.getUserEntity() != null) {
            dto.setFullName(entity.getUserEntity().getFullName());
        } else {
            AccountEntity accountEntity = accountRepository.findById(entity.getUserId()).orElse(null);
            if (accountEntity != null) {
                dto.setFullName(accountEntity.getFullName());
            }
        }

        return dto;
    }
}