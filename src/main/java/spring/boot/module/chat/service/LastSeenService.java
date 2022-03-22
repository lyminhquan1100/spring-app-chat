package spring.boot.module.chat.service;

import spring.boot.core.service.BaseService;
import spring.boot.module.chat.dto.LastSeenDTO;

public interface LastSeenService extends BaseService<LastSeenDTO> {
    void lastSeen(Long userId, Long roomId, Long messageId);
}