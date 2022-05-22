package spring.boot.module.chat.service;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import spring.boot.module.chat.entity.RoomEntity;
import spring.boot.module.chat.enums.PackEnum;

import java.util.List;

public interface PackService {
    void sendToUser(Long userId, PackEnum type, Object data);

    void sendToUser(List<Long> userId, PackEnum type, Object data);

    void sendToDevice(Long deviceId, PackEnum type, Object data);

    void sendPublic(PackEnum type, Object data);

    void sendBackCurrentDevice(SimpMessageHeaderAccessor headerAccessor, PackEnum type, Object data);

    void sendToRoom(RoomEntity roomEntity, PackEnum type, Object data);

    void sendToRoom(Long roomId, PackEnum type, Object data);
}
