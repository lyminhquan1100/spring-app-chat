package spring.boot.module.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import spring.boot.core.api.CoreServiceImpl;
import spring.boot.core.exception.BaseException;
import spring.boot.module.auth.entity.AccountEntity;
import spring.boot.module.auth.repository.AccountRepository;
import spring.boot.module.auth.service.AccountService;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.dto.RoomDTO;
import spring.boot.module.chat.entity.MessageEntity;
import spring.boot.module.chat.entity.RoomEntity;
import spring.boot.module.chat.enums.PackEnum;
import spring.boot.module.chat.repository.LastSeenRepository;
import spring.boot.module.chat.repository.MessageRepository;
import spring.boot.module.chat.repository.RoomRepository;
import spring.boot.module.chat.utils.Destinations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl extends CoreServiceImpl<RoomDTO, RoomEntity>
        implements RoomService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private PackService packService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LastSeenRepository lastSeenRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    @Override
    protected void beforeSave(RoomEntity entity, RoomDTO dto) {
        super.beforeSave(entity, dto);

    }

    private RoomDTO joinOrLeave(Long userId, Long roomId, boolean isJoin) {
        if (userId == null) {
            throw new BaseException("Vui lòng nhập userId");
        }
        if (roomId == null) {
            throw new BaseException("Vui lòng nhập roomId");
        }
        RoomEntity roomEntity = getRepository().findById(roomId).orElse(null);
        if (userId == roomEntity.getAdminId()) {
            sendMessage(accountService.getCurrentUserId(), Destinations.sendData("warning", "Quản trị viên đã trong nhóm"));
            return null;
        }
        for (int i = 0; i < roomEntity.getConnectedUsers().size(); i++) {
            if (roomEntity.getConnectedUsers().get(i).getId() == userId) {
                sendMessage(accountService.getCurrentUserId(), Destinations.sendData("warning", "Người dùng đang trong nhóm"));
                return null;
            }
        }
        AccountEntity accountEntity = accountRepository.findById(userId).orElse(null);
        if (roomEntity != null) {
            if (isJoin) {
                roomEntity.addUser(accountEntity);
            } else {
                roomEntity.removeUser(accountEntity);
            }
            RoomDTO roomDTO = new RoomDTO();
            save(roomEntity, roomDTO);
            if (roomEntity.getConnectedUsers() == null) {
                List<AccountEntity> newList = new ArrayList<>();
                roomEntity.setConnectedUsers(newList);
            }
//            List<Long> userIds = roomEntity.getConnectedUsers()
//                    .stream().map(e -> e.getId()).collect(Collectors.toList());
//            userIds.add(roomEntity.getAdminId());
//            SendDTO sendDTO = new SendDTO("addUser", accountEntity);
            sendMessage(userId, Destinations.sendData("join", roomEntity));
//            sendMessage(userIds, Destinations.sendData("addUser", roomEntity));
        }
        return mapToDTO(roomEntity);
    }

    @Override
    public RoomDTO join(Long userId, Long roomId) {
        return joinOrLeave(userId, roomId, true);
    }

    @Override
    public RoomDTO leave(Long userId, Long roomId) {
        return joinOrLeave(userId, roomId, false);
    }

    private void sendMessage(Long userId, Map<String, Object> sendDto) {
        simpMessagingTemplate.convertAndSend(
                Destinations.userPack(userId), sendDto);

    }

    private void sendMessage(List<Long> userIds, Map<String, Object> sendDto) {
        for (int i = 0; i < userIds.size(); i++) {
            simpMessagingTemplate.convertAndSend(
                    Destinations.userPack(userIds.get(i)), sendDto);
        }
    }

    @Override
    public void listRoom(Long userId) {
        List<RoomDTO> roomDTOList = roomRepository
                .listRoomByUserId(userId)
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
        packService.sendToUser(userId, PackEnum.LIST_ROOM, roomDTOList);
    }

    @Override
    public RoomDTO mapToDTO(RoomEntity entity) {
        RoomDTO dto = super.mapToDTO(entity);

        if (dto.getLastMessageId() != null) {
            MessageEntity messageEntity = messageRepository
                    .findById(dto.getLastMessageId()).orElse(null);
            if (messageEntity != null) {
                dto.setLastMessage(messageService.mapToDTO(messageEntity));
            }
        }
        if (entity.getAdminId() != null) {
            AccountEntity accountEntity = accountRepository.findById(
                    entity.getAdminId()
            ).orElse(null);
            dto.setAdmin(accountEntity);
        }
        return dto;
    }
}
