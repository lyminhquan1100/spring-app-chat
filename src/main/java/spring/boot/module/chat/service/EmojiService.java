package spring.boot.module.chat.service;

import spring.boot.core.api.CoreService;
import spring.boot.module.chat.dto.EmojiDTO;
import spring.boot.module.chat.entity.EmojiEntity;

public interface EmojiService extends CoreService<EmojiDTO, EmojiEntity> {
    void sendEmoji(EmojiDTO emojiDTO);
}