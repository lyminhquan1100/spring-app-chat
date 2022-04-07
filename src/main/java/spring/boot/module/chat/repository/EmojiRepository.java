package spring.boot.module.chat.repository;


import org.springframework.stereotype.Repository;
import spring.boot.core.api.CoreRepository;
import spring.boot.module.chat.dto.EmojiDTO;
import spring.boot.module.chat.entity.EmojiEntity;

@Repository
public interface EmojiRepository extends CoreRepository<EmojiDTO, EmojiEntity> {
    EmojiEntity findByUserIdAndMessageId(Long userId,Long messageId);
}