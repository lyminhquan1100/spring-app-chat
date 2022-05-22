package spring.boot.module.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import spring.boot.core.exception.BaseException;
import spring.boot.core.security.userdetail.UserPrincipal;
import spring.boot.module.auth.entity.AccountEntity;
import spring.boot.module.chat.dto.PackDTO;
import spring.boot.module.chat.entity.RoomEntity;
import spring.boot.module.chat.enums.PackEnum;
import spring.boot.module.chat.utils.Destinations;

import java.util.List;

@Service
public class PackSocketService implements PackService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RoomService roomService;

    @Override
    public void sendToUser(Long userId, PackEnum type, Object payload) {
        simpMessagingTemplate.convertAndSend(
                Destinations.userPack(userId),
                new PackDTO(type, payload));
    }

    @Override
    public void sendToUser(List<Long> userId, PackEnum type, Object data) {
        for (Long id : userId) {
            sendToUser(id, type, data);
        }
    }

    @Override
    public void sendToDevice(Long deviceId, PackEnum type, Object payload) {
        simpMessagingTemplate.convertAndSend(
                Destinations.devicePack(deviceId),
                new PackDTO(type, payload));
    }

    @Override
    public void sendPublic(PackEnum type, Object data) {
        simpMessagingTemplate.convertAndSend(
                Destinations.publicPack(),
                new PackDTO(type, data));
    }

    @Override
    public void sendBackCurrentDevice(SimpMessageHeaderAccessor headerAccessor, PackEnum type, Object data) {
        Long deviceInfoId = (Long) headerAccessor.getSessionAttributes().get("deviceInfoId");
        assert deviceInfoId != null;
        sendToDevice(deviceInfoId, type, data);
    }

    @Override
    public void sendToRoom(RoomEntity roomEntity, PackEnum type, Object data) {
        sendToUser(roomEntity.getAdminId(), type, data);
        List<AccountEntity> listUser = roomEntity.getConnectedUsers();
        if (listUser != null) {
            for (AccountEntity accountEntity : listUser) {
                sendToUser(accountEntity.getId(), type, data);
            }
        }
    }

    @Override
    public void sendToRoom(Long roomId, PackEnum type, Object data) {
        RoomEntity roomEntity = roomService.getById(roomId);
        sendToRoom(roomEntity, type, data);
    }
}
