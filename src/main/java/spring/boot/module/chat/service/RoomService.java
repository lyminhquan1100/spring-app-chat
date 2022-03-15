package spring.boot.module.chat.service;

import spring.boot.core.service.BaseService;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.dto.RoomDTO;

public interface RoomService extends BaseService<RoomDTO> {
    RoomDTO join(AccountDTO joiningUser, RoomDTO chatRoom);
    RoomDTO leave(AccountDTO leavingUser, RoomDTO chatRoom);

    void sendPublicMessage(MessageDTO dto);

    void sendPrivateMessage(MessageDTO dto);
}
