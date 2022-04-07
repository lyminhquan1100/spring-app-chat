package spring.boot.module.chat.service;

import spring.boot.core.api.CoreService;
import spring.boot.module.chat.dto.LastSeenDTO;
import spring.boot.module.chat.entity.LastSeenEntity;

public interface LastSeenService extends CoreService<LastSeenDTO, LastSeenEntity> {
    void lastSeen(Long userId, Long roomId, Long messageId);
}