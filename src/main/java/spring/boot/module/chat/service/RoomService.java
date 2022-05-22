package spring.boot.module.chat.service;

import spring.boot.core.api.CoreService;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.dto.RoomDTO;
import spring.boot.module.chat.entity.RoomEntity;

import java.util.Map;

public interface RoomService extends CoreService<RoomDTO, RoomEntity> {
    RoomDTO join(Long userId, Long roomId);

    RoomDTO join(Long roomId, RoomDTO roomDTO);
    RoomDTO leave(Long userId, Long roomId);
    void listRoom(Long userID);

    RoomDTO create(RoomDTO roomDTO);
}
