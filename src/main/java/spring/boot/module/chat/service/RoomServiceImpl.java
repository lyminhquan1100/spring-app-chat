package spring.boot.module.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import spring.boot.core.exception.BaseException;
import spring.boot.core.service.AbstractBaseService;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.auth.entity.AccountEntity;
import spring.boot.module.auth.repository.AccountRepository;
import spring.boot.module.auth.service.AccountService;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.dto.RoomDTO;
import spring.boot.module.chat.entity.RoomEntity;
import spring.boot.module.chat.repository.RoomRepository;
import spring.boot.module.chat.utils.Destinations;
import spring.boot.module.chat.utils.SystemMessages;

@Service
public class RoomServiceImpl extends AbstractBaseService<RoomEntity, RoomDTO, RoomRepository> implements RoomService {

    @Autowired
    private SimpMessagingTemplate webSocketMessagingTemplate;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @Override
    protected RoomRepository getRepository() {
        return roomRepository;
    }

    @Override
    public RoomDTO join(AccountDTO joiningUser, RoomDTO chatRoom) {
        RoomEntity roomEntity = getRepository().findById(chatRoom.getId()).orElse(null);
        if(roomEntity == null){
            throw new BaseException("Id phòng không tồn tại");
        }
        AccountEntity accountEntity = accountRepository.findById(joiningUser.getId()).orElse(null);
        roomEntity.addUser(accountEntity);
        getRepository().save(roomEntity);

        sendPublicMessage(SystemMessages.welcome(chatRoom.getId(), joiningUser.getUsername()));
        updateConnectedUsersViaWebSocket(chatRoom);
        return chatRoom;
    }

    @Override
    public RoomDTO leave(AccountDTO leavingUser, RoomDTO chatRoom) {
        RoomEntity roomEntity = getRepository().findById(chatRoom.getId()).orElse(null);
        if(roomEntity == null){
            throw new BaseException("Id phòng không tồn tại");
        }
        AccountEntity accountEntity = accountRepository.findById(leavingUser.getId()).orElse(null);
        roomEntity.removeUser(accountEntity);
        getRepository().save(roomEntity);

        sendPublicMessage(SystemMessages.goodbye(chatRoom.getId(), leavingUser.getUsername()));
        updateConnectedUsersViaWebSocket(chatRoom);
        return chatRoom;
    }

    @Override
    public void sendPublicMessage(MessageDTO instantMessage) {
        webSocketMessagingTemplate.convertAndSend(
                Destinations.ChatRoom.publicMessages(instantMessage.getRoomId()),
                instantMessage);

        messageService.save(instantMessage);
    }

    @Override
    public void sendPrivateMessage(MessageDTO instantMessage) {
        webSocketMessagingTemplate.convertAndSendToUser(
                instantMessage.getToUser().toString(),
                Destinations.ChatRoom.privateMessages(instantMessage.getRoomId()),
                instantMessage);

        webSocketMessagingTemplate.convertAndSendToUser(
                instantMessage.getCreatedBy().toString(),
                Destinations.ChatRoom.privateMessages(instantMessage.getRoomId()),
                instantMessage);

        messageService.save(instantMessage);
    }

    private void updateConnectedUsersViaWebSocket(RoomDTO roomDTO) {
        webSocketMessagingTemplate.convertAndSend(
                Destinations.ChatRoom.connectedUsers(roomDTO.getId()),
                roomDTO.getConnectedUsers());
    }
}
