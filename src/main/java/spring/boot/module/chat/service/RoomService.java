package spring.boot.module.chat.service;

import spring.boot.core.service.BaseService;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.dto.RoomDTO;

import java.util.Map;

public interface RoomService extends BaseService<RoomDTO> {
    RoomDTO join(Long userId, Long roomId);
    RoomDTO leave(Long userId, Long roomId);
    void sendMessage(MessageDTO roomDTO);
    void sendMessage(Long roomId,String type,Object payload);
    void subscribe(Map<String,Object> data);
    void subscribe(Long data);
    void listRoom(Long userID);
}
